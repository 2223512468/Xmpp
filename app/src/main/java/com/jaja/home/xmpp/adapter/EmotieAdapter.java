package com.jaja.home.xmpp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.base.BaseListAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by ${Terry} on 2017/12/22.
 */
public class EmotieAdapter extends BaseListAdapter<String> {

    private Context mCotnext;

    public EmotieAdapter(Context context, List list) {
        super(context, list);
        this.mCotnext = context;
    }

    @Override
    public View initView(String s, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.act_emotie_item, null);
            holder = new ViewHolder();
            holder.pImv = (ImageView) convertView.findViewById(R.id.p);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            Bitmap pBit = BitmapFactory.decodeStream(mCotnext.getAssets().open("p/" + s));
            holder.pImv.setImageBitmap(pBit);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        ImageView pImv;
    }

}
