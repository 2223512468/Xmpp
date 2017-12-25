package com.jaja.home.xmpp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *  BaseAdapter 的基类封装 ，可用于list view grid view
 */
public abstract class BaseListAdapter<T> extends android.widget.BaseAdapter {

    protected Context context;
    protected LayoutInflater inflater;
    /**
     * list 集合
     */
    protected List<T> list;

    public BaseListAdapter(Context context, List<T> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 重置数据
     * @param list :list 集合
     */
    public void setItems(List<T> list) {
        if(this.list!=null){
            this.list.clear();
        }
        this.list = list;
        notifyDataSetChanged();
    }
    /**
     * 清空数据
     */
    public void clearItems() {
        if(this.list!=null){
            this.list.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list==null)return 0;
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(list.get(position),convertView,parent);
    }

    public abstract View initView(T t, View convertView, ViewGroup parent);
}
