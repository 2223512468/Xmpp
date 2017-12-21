package com.jaja.home.xmpp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaja.home.xmpp.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${Terry} on 2017/12/19.
 */
public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder> {


    private Context mContext;
    private List<String> datasList;
    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public HeaderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(String[] datas) {
        this.datasList = Arrays.asList(datas);
        notifyDataSetChanged();
    }

    public String getDatas(int position) {
        return datasList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_header, null);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getAssets().open("head/" + datasList.get(position)));
            holder.img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return datasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.item_iv);
            img.setOnClickListener(new View.OnClickListener() {
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
        void onClick(View view, int position);
    }

}
