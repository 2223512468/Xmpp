package com.jaja.home.xmpp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：RecycleView adapter的基类
 * 创建人：admin
 * 创建时间：2016/5/31 13:40
 * 修改人：admin
 * 修改时间：2016/5/31 13:40
 * 修改备注：
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    public List<T> getDataList() {
        return mList;
    }


    /**
     * l数据集合
     */
    public List<T> mList = new ArrayList<>();

    protected Context mContext;
    private OnItemClickedListener mListener;
    private OnItemLongClickListener mLongListener;


    /**
     * 设置长按事件监听
     * @param mLongListener ：listener
     */
    public void setmLongListener(OnItemLongClickListener mLongListener) {
        this.mLongListener = mLongListener;
    }

    private int mLayoutId;

    /**
     * 添加数据
     *
     * @param list ：要添加的数据集合
     */
    public void addItems(List<T> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }
    /**
     * 添加数据
     *
     * @param list ：要添加的数据集合
     */
    public void addItems(ArrayList<T> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 模拟10条数据
     * @param s ：模拟数据
     */
    public void test(T s) {
        for (int i = 0; i < 10; i++) {
            mList.add(s);
        }
        notifyDataSetChanged();
    }

    /**
     * 重置数据
     *
     * @param list ：要添加的数据集合
     */
    public void resetItems(List<T> list) {
        if (mList != null) {
            this.mList.clear();
        } else {
            this.mList = new ArrayList<>();
        }
        if (list != null)
            this.mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清空省部级
     */
    public void clear() {
        if (mList != null&&mList.size()>0){
            this.mList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 修改单个item
     * @param index :index
     * @param t :t
     */
    public void changeItem(int index, T t) {
        if (mList != null && mList.size() > index) {
            mList.set(index, t);
            notifyDataSetChanged();
        }
    }

    public void setOnItemOnListener(OnItemClickedListener mListener) {
        this.mListener = mListener;
    }


    protected Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClicked(mLayoutId, i);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongListener != null) {
                    mLongListener.OnItemLongClick(mLayoutId, i);
                }
                return true;
            }
        });

        showData(viewHolder, i, mList);
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        mLayoutId = getListLayoutId();
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, viewGroup, false);
        BaseRecyclerViewHolder holder = createViewHolder(view);
        return holder;
    }

    /**
     *
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    public abstract void showData(BaseRecyclerViewHolder viewHolder, int i, List<T> mItemDataList);

    /**
     *
     * @return int
     */
    public abstract int getListLayoutId();

    /**
     *
     * @param view item_frag_home_drag 的view
     * @return RecyclerViewHolderBase 基类ViewHolder
     */
    public abstract BaseRecyclerViewHolder createViewHolder(View view);

    public void remove(int i) {
        if (mList != null && mList.size() > i) {
            mList.remove(i);
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int id, int index);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(int id, int index);
    }

}
