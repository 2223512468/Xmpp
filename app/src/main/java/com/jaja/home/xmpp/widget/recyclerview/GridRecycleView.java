package com.jaja.home.xmpp.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.jaja.home.xmpp.util.Contants;


/**
 * 类描述：可 添加Footview Headview 可下拉刷新 加载更多的RecycleView
 * 创建人：admin
 * 创建时间：2016/5/31 9:31
 * 修改人：admin
 * 修改时间：2016/5/31 9:31
 * 修改备注：
 */
public class GridRecycleView extends LinearLayout {

    /**
     * 分割线颜色
     */
    private int mDivColor;
    /**
     * 分割线高度
     */
    private float mDivHeight;
    /**
     * 分割线距边缘距离
     */
    private float mDivMargin;
    private LoadingFooter.State mState = LoadingFooter.State.Normal;//当前列表的状态

    private RecyclerView mRecyclerView;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private Context mContext;
    private OnMutilRecyclerViewListener listener;
    private boolean loadMoreable = true;
    private boolean isGrid = false;

    public GridRecycleView(Context context) {
        super(context);
        init(context);
    }

    public GridRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }





    public void setOnMutilRecyclerViewListener(OnMutilRecyclerViewListener listener) {
        this.listener = listener;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }

    /**
     * 添加顶部View（在添加底部view后 加载更多功能不可用）
     *
     * @param view :the view
     */
    public void addHeaderView(View view) {
        if (mHeaderAndFooterRecyclerViewAdapter != null)
            mHeaderAndFooterRecyclerViewAdapter.addHeaderView(view);
    }

    /**
     * 添加底部View（在添加底部view后 加载更多功能不可用）
     *
     * @param view :the view
     */
    public void addFooterView(View view) {
        if (mHeaderAndFooterRecyclerViewAdapter != null)
            mHeaderAndFooterRecyclerViewAdapter.addFooterView(view);
    }

    public void setDiv(float divMargin, int color) {
        mDivMargin = divMargin;
        if (mDivHeight > 0) {
            mDivColor = color;
            mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).margin((int) mDivMargin).color(mDivColor).build());
        }
    }

    /**
     * 设置是否可下拉刷新
     *
     * @param enable
     */
    public void setRefreshEnable(boolean enable) {
        setEnabled(enable);
    }

    /**
     * 设置是否可下拉刷新
     *
     * @param loadMoreable
     */
    public void setLoadMoreable(boolean loadMoreable) {
        this.loadMoreable = loadMoreable;
    }


    /**
     * LinearLayoutManager
     */
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * LinearLayoutManager
     */
    public void setGridLayout(int num) {
        //setLayoutManager
//        ExStaggeredGridLayoutManager manager = new ExStaggeredGridLayoutManager (num, StaggeredGridLayoutManager.VERTICAL);
//        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
//        mRecyclerView.setLayoutManager(manager);

        GridLayoutManager manager = new GridLayoutManager(mContext, num);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup(mHeaderAndFooterRecyclerViewAdapter, manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);
    }




    public void stopLoadingMore() {
        if (mState == LoadingFooter.State.LoadingMore) {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
            setRefreshEnable(true);
        }
    }

    protected void init(Context context) {
        mContext = context;
        initRecycleView();
    }

    protected void initRecycleView() {
        mRecyclerView = new RecyclerView(mContext);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mRecyclerView.setLayoutParams(params);
        addView(mRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        if (!isGrid) {
            mRecyclerView.setVerticalScrollBarEnabled(true);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            setLinearLayout();
        }
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (mState == LoadingFooter.State.Loading || mState == LoadingFooter.State.LoadingMore) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }
            if (!loadMoreable) {
                return;
            }
            boolean isloadmore = RecyclerViewStateUtils.setFooterViewState(mContext, mRecyclerView, Contants.PAGE_LIMIT, LoadingFooter.State.LoadingMore, null);
            if (isloadmore) {
                mState = LoadingFooter.State.LoadingMore;
                if (listener != null)
                    listener.onLoadMore();
            }
        }
    };


    public interface OnMutilRecyclerViewListener {
        void onRefresh();

        void onLoadMore();
    }
}
