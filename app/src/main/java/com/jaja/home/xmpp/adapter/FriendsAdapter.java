package com.jaja.home.xmpp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.entity.GroupEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${Terry} on 2017/12/21.
 */
public class FriendsAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<GroupEntity> dataList;

    public FriendsAdapter(Context mContext) {
        this.mContext = mContext;
        this.dataList = new ArrayList<>();
    }

    public void setData(List<GroupEntity> dataList) {
        this.dataList = dataList;
    }


    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return dataList.get(i).getFriends().size();
    }

    @Override
    public Object getGroup(int i) {
        return dataList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return dataList.get(i).getFriends().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = null;
        if (convertView != null) {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        } else {
            groupViewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
            groupViewHolder.groupName = (TextView) convertView.findViewById(R.id.group_name);
            groupViewHolder.groupOffLine = (TextView) convertView.findViewById(R.id.group_offline);
            groupViewHolder.groupCount = (TextView) convertView.findViewById(R.id.group_friend_count);
            convertView.setTag(groupViewHolder);
        }

        groupViewHolder.groupName.setText(dataList.get(groupPosition).getGroupName());
        groupViewHolder.groupCount.setText(getChildrenCount(groupPosition) + "");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;
        if (convertView != null) {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friend, null);
            childViewHolder.circleImageView = (CircleImageView) convertView.findViewById(R.id.search_head_img);
            childViewHolder.nickName = (TextView) convertView.findViewById(R.id.search_nickName_tv);
            childViewHolder.sign = (TextView) convertView.findViewById(R.id.search_sign_tv);
            convertView.setTag(childViewHolder);
        }

        String str = dataList.get(groupPosition).getFriends().get(childPosition).getHead();
        childViewHolder.nickName.setText(dataList.get(groupPosition).getFriends().get(childPosition).getNickName());
        childViewHolder.sign.setText(dataList.get(groupPosition).getFriends().get(childPosition).getSignature());
        try {
            childViewHolder.circleImageView.setImageBitmap(BitmapFactory.decodeStream(mContext.getAssets().open("head/" + str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    class GroupViewHolder {
        TextView groupName, groupOffLine, groupCount;
    }

    class ChildViewHolder {
        CircleImageView circleImageView;
        TextView nickName, sign;
    }

}
