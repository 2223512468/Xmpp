package com.jaja.home.xmpp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.entity.UserEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${Terry} on 2017/12/21.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private Context mContext;
    private List<UserEntity> dataList;
    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public SearchResultAdapter(Context mContext) {
        this.mContext = mContext;
        this.dataList = new ArrayList<>();
    }

    public void setData(List<UserEntity> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public UserEntity getItem(int position) {
        return dataList.get(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.act_search_result_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserEntity userEntity = dataList.get(position);
        try {
            Bitmap headBit = BitmapFactory.decodeStream(mContext.getAssets().open("head/" + userEntity.getHead()));
            holder.circleImageView.setImageBitmap(headBit);
            holder.searchNickNameTv.setText(userEntity.getUserName());
            holder.searchSignTv.setText(userEntity.getSignature());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView searchNickNameTv, searchSignTv;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.search_head_img);
            searchNickNameTv = (TextView) itemView.findViewById(R.id.search_nickName_tv);
            searchSignTv = (TextView) itemView.findViewById(R.id.search_sign_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(view, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }

}
