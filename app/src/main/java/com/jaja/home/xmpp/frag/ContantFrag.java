package com.jaja.home.xmpp.frag;

import android.util.Log;
import android.widget.ExpandableListView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.adapter.FriendsAdapter;
import com.jaja.home.xmpp.entity.GroupEntity;
import com.jaja.home.xmpp.entity.UserEntity;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${Terry} on 2017/12/20.
 */
public class ContantFrag extends BaseFragment {

    @BindView(R.id.con_listView)
    ExpandableListView listView;
    private FriendsAdapter adapter;


    @Override
    protected int getViewId() {
        return R.layout.act_frag;
    }

    @Override
    protected void init() {

        List<GroupEntity> groupList = XmppUtils.queryFriendGroup();
        adapter = new FriendsAdapter(mContext);
        listView.setAdapter(adapter);
        adapter.setData(groupList);
        for (GroupEntity entity : groupList) {
            Log.i("print", entity.getGroupName());
            List<UserEntity> friends = entity.getFriends();
            for (UserEntity uEntity : friends) {
                Log.i("print", uEntity.getNickName());
            }
        }
    }
}
