package com.jaja.home.xmpp.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.base.BaseRecyclerAdapter;
import com.jaja.home.xmpp.base.BaseRecyclerViewHolder;
import com.jaja.home.xmpp.entity.MsgEntity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${Terry} on 2017/12/23.
 */
public class ChatAdapter extends BaseRecyclerAdapter<MsgEntity> {

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<MsgEntity> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        int type = mItemDataList.get(i).getmType();
        String msg = mItemDataList.get(i).getMsg();
        String username = mItemDataList.get(i).getUsername();
        if (type == 0) {
            holder.fromL.setVisibility(View.VISIBLE);
            holder.fromT.setText(msg);
            holder.fromNameT.setText(username);
            holder.toL.setVisibility(View.GONE);
        } else {
            holder.fromL.setVisibility(View.GONE);
            holder.toL.setVisibility(View.VISIBLE);
            holder.toNameT.setText(username);
            holder.toT.setText(msg);
        }

    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_chat_msg;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    public class ViewHolder extends BaseRecyclerViewHolder {

        RelativeLayout fromL, toL;
        CircleImageView fromV, toV;
        TextView fromT, toT;
        TextView fromNameT, toNameT;

        public ViewHolder(View itemView) {
            super(itemView);
            fromL = (RelativeLayout) itemView.findViewById(R.id.chat_left);
            toL = (RelativeLayout) itemView.findViewById(R.id.chat_right);
            fromV = (CircleImageView) itemView.findViewById(R.id.chat_head_from_iv);
            toV = (CircleImageView) itemView.findViewById(R.id.chat_head_to_iv);
            fromT = (TextView) itemView.findViewById(R.id.chat_msg_from_tv);
            toT = (TextView) itemView.findViewById(R.id.chat_msg_to_tv);
            fromNameT = (TextView) itemView.findViewById(R.id.chat_from_name_tv);
            toNameT = (TextView) itemView.findViewById(R.id.chat_to_name_tv);
        }
    }
}
