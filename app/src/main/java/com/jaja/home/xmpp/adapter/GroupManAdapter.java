package com.jaja.home.xmpp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.base.BaseRecyclerAdapter;
import com.jaja.home.xmpp.base.BaseRecyclerViewHolder;
import com.jaja.home.xmpp.entity.GroupEntity;

import java.util.List;

/**
 * Created by ${Terry} on 2017/12/25.
 */
public class GroupManAdapter extends BaseRecyclerAdapter<GroupEntity> {

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<GroupEntity> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.groupName.setText(mItemDataList.get(i).getGroupName());
    }

    @Override
    public int getListLayoutId() {
        return R.layout.act_item_group_man;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView addImg;
        TextView groupName;

        public ViewHolder(View itemView) {
            super(itemView);
            addImg = (ImageView) itemView.findViewById(R.id.group_add);
            groupName = (TextView) itemView.findViewById(R.id.group_name);

        }
    }
}
