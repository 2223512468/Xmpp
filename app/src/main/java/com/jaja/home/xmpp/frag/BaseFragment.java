package com.jaja.home.xmpp.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by ${Terry} on 2017/12/20.
 */
public abstract class BaseFragment extends Fragment {
    protected ProgressDialog mDialog;
    public Context mContext;

    protected abstract int getViewId();

    protected abstract void init();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view;
        if (getViewId() != 0) {
            view = inflater.inflate(getViewId(), null);
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    /**
     * 显示动画弹窗
     *
     * @param text
     */
    protected void showDialog(@NonNull String text) {
        if (null == mDialog) {
            mDialog = new ProgressDialog(getActivity());
        }
        mDialog.setMessage(text);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 去动画弹窗
     */
    protected void dissmissDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 打开一个新的activity
     *
     * @param cla
     */
    protected void startActivity(Class<?> cla) {
        Intent intent = new Intent(getActivity(), cla);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
