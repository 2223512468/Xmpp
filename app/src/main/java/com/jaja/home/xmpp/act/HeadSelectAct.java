package com.jaja.home.xmpp.act;

import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.adapter.HeaderAdapter;
import com.jaja.home.xmpp.base.BaseActivity;
import com.jaja.home.xmpp.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${Terry} on 2017/12/19.
 */
public class HeadSelectAct extends BaseActivity implements HeaderAdapter.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.headImg)
    CircleImageView img;

    private HeaderAdapter adapter;
    private String str;


    @Override
    protected int getViewId() {
        return R.layout.act_head_select;
    }

    @Override
    protected void initEvent() {

        adapter = new HeaderAdapter(mContext);
        adapter.setListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(adapter);
        loadDatas();
    }


    @OnClick({R.id.ok})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                if (StringUtil.isEmpty(str)) {
                    Toast.makeText(mContext, "请选择头像", Toast.LENGTH_SHORT).show();
                    return;
                }
                EventBus.getDefault().post(str);
                finish();
                break;
        }
    }


    private void loadDatas() {
        try {
            String[] headers = getAssets().list("head");
            adapter.setData(headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view, int position) {
        str = adapter.getDatas(position);
        Log.i("print", str);
        try {
            img.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("head/" + str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
