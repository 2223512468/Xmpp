package com.jaja.home.xmpp.app;

import android.app.Application;

import com.jaja.home.xmpp.util.SharedUtil;

/**
 * Created by ${Terry} on 2017/12/20.
 */
public class appContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedUtil.init(this);
    }
}
