package com.jaja.home.xmpp.act;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.adapter.GroupManAdapter;
import com.jaja.home.xmpp.base.BaseActivity;
import com.jaja.home.xmpp.entity.GroupEntity;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;
import com.jaja.home.xmpp.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${Terry} on 2017/12/25.
 */
public class GroupManAct extends BaseActivity implements GroupManAdapter.OnItemClickedListener {

    @BindView(R.id.group_commit)
    TextView commitTv;
    @BindView(R.id.recyclerView)
    MultiRecycleView multiRecycleView;
    private GroupManAdapter adapter;
    private AlertDialog dialog;

    @Override
    protected int getViewId() {
        return R.layout.act_group_manager;
    }

    @Override
    protected void initEvent() {
        overridePendingTransition(R.anim.anim_login_layout, 0);
        multiRecycleView.setRefreshEnable(false);
        adapter = new GroupManAdapter();
        multiRecycleView.setAdapter(adapter);
        multiRecycleView.addHeaderView(getHeader());
        adapter.setOnItemOnListener(this);

        loadData();
    }

    private void loadData() {
        List<GroupEntity> groupList = XmppUtils.queryFriendGroup();
        Log.i("print", "groupList" + groupList.size());
        adapter.addItems(groupList);

    }

    private View getHeader() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.act_item_group_man, null);
        ImageView addImg = (ImageView) rootView.findViewById(R.id.group_add);
        TextView groupName = (TextView) rootView.findViewById(R.id.group_name);
        groupName.setText("添加分组");
        return rootView;
    }


    @Override
    public void onItemClicked(int id, int index) {

        Log.i("print", "group" + id);
        View v = LayoutInflater.from(mContext).inflate(R.layout.act_group_man_dialog, null);
        dialog = new AlertDialog.Builder(this)
                .create();
        dialog.setView(v);
        dialog.show();
    }
}
