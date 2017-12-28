package com.jaja.home.xmpp.pop;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.act.GroupManAct;


/**
 * 类描述：弹出分享 （只能是url 获取text中一种）
 * 创建人：admin
 * 创建时间：2016/4/25 16:19
 * 修改人：admin
 * 修改时间：2016/4/25 16:19
 * 修改备注：
 */
public class PopShare {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private View view;

    public PopShare(Context context, View view) {
        this.mContext = context;
        this.view = view;
        init();
    }


    protected void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_group_man, null);
        TextView groupMan = (TextView) rootView.findViewById(R.id.group_manager);
        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.half_translate));
        mPopupWindow.setFocusable(true);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, GroupManAct.class);
                mContext.startActivity(intent);
            }
        });

    }


    /**
     * 显示popupwindow
     */
    public void show() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 显示popupwindow
     *
     * @param view
     */
    public void show(View view, String text) {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, 0, 0, 0);
        }
    }

    /**
     * 关闭popupwindow
     */
    public void dismiss() {
        if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
    }


    protected void showToast(String str) {
        //去换行
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

}
