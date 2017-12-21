package com.jaja.home.xmpp.act;

import android.util.Log;
import android.widget.ImageView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.util.XmppConnectionUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/19.
 */
public class WelcomeAct extends BaseActivity {
    
    @BindView(R.id.splashImv)
    ImageView splashImv;

    @Override
    protected int getViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEvent() {
        new Thread() {
            @Override
            public void run() {
                Boolean flag = XmppConnectionUtils.openConnection();
                Log.i("print", "连接服务器" + flag);
            }
        }.start();

        splashImv.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoNewAty(LoginAct.class);
                finish();
            }
        }, 2000);

    }
}
