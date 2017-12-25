package com.jaja.home.xmpp.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.act.ChatAct;
import com.jaja.home.xmpp.adapter.FriendsAdapter;
import com.jaja.home.xmpp.base.BaseFragment;
import com.jaja.home.xmpp.entity.GroupEntity;
import com.jaja.home.xmpp.entity.UserEntity;
import com.jaja.home.xmpp.util.Contants;
import com.jaja.home.xmpp.util.StringUtil;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${Terry} on 2017/12/20.
 */
public class ContantFrag extends BaseFragment implements ExpandableListView.OnChildClickListener {

    @BindView(R.id.con_listView)
    ExpandableListView listView;
    private FriendsAdapter adapter;
    private LocationBroadcastReceiver mLocationBroadcastReceiver = new LocationBroadcastReceiver();

    @Override
    protected int getViewId() {
        return R.layout.act_frag;
    }

    @Override
    protected void init() {

        adapter = new FriendsAdapter(mContext);
        listView.setAdapter(adapter);
        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.LINE);
        mContext.registerReceiver(mLocationBroadcastReceiver, filter);
        listView.setOnChildClickListener(this);

        loadData();

    }

    private void loadData() {
        List<GroupEntity> groupList = XmppUtils.queryFriendGroup();
        adapter.setData(groupList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationBroadcastReceiver != null) {
            mContext.unregisterReceiver(mLocationBroadcastReceiver);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
        UserEntity user = (UserEntity) adapter.getChild(groupPosition, childPosition);
        Intent intent = new Intent(getActivity(), ChatAct.class);
        intent.putExtra(ChatAct.USERNAME, user);
        startActivity(intent);
        return true;
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(Contants.LINE)) return;
            Log.i("print", "line");
            Boolean OnLine = intent.getBooleanExtra(Contants.ISONLINE, false);
            if (!StringUtil.isEmpty(OnLine.toString())) {
                loadData();
            }
        }
    }

}
