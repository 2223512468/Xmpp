package com.jaja.home.xmpp.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类描述：RecyclerViewHolder的基类
 * 创建人：admin
 * 创建时间：2016/5/31 13:46
 * 修改人：admin
 * 修改时间：2016/5/31 13:46
 * 修改备注：
 */
public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private View root;
    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        root=itemView;
    }
    public View findView(int id){
        return root.findViewById(id);
   }
}
