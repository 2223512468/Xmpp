package com.jaja.home.xmpp.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.base.BaseActivity;
import com.jaja.home.xmpp.entity.RegisterUser;
import com.jaja.home.xmpp.entity.UserEntity;
import com.jaja.home.xmpp.service.CoreSerice;
import com.jaja.home.xmpp.util.Contants;
import com.jaja.home.xmpp.util.SharedUtil;
import com.jaja.home.xmpp.util.StringUtil;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/12/19.
 */
public class LoginAct extends BaseActivity {

    @BindView(R.id.loginL)
    LinearLayout loginL;
    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_login)
    Button loginBtn;
    @BindView(R.id.headCircle)
    CircleImageView headCircle;


    @Override
    protected int getViewId() {
        return R.layout.act_login;
    }

    @Override
    protected void initEvent() {

        startService(new Intent(mContext, CoreSerice.class));
        EventBus.getDefault().register(this);
        String username = SharedUtil.getString(Contants.USERNAME);
        String password = SharedUtil.getString(Contants.PASSWORD);
        String header = SharedUtil.getString(Contants.HEADER);

        if (!StringUtil.isEmpty(username) && !StringUtil.isEmpty(password)) {
            editUser.setText(username);
            editPwd.setText(password);
        }

        if (!StringUtil.isEmpty(header)) {
            try {
                Bitmap headBit = BitmapFactory.decodeStream(getAssets().open("head/" + header));
                headCircle.setImageBitmap(headBit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.tv_register, R.id.tv_forget_pwd, R.id.btn_login})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                gotoNewAty(RegisterAct.class);
                break;
            case R.id.tv_forget_pwd:
                break;
            case R.id.btn_login:
                String username = editUser.getText().toString();
                String password = editPwd.getText().toString();
                if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
                    Toast.makeText(mContext, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                Boolean isLogin = XmppUtils.login(username, password);
                if (isLogin) {
                    SharedUtil.putString(Contants.USERNAME, username);
                    SharedUtil.putString(Contants.PASSWORD, password);
                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                    //获取用户基本信息
                    UserEntity userInfo = XmppUtils.getUserInfo();
                    if (userInfo == null) {
                        gotoNewAty(ConfigAct.class);
                    } else {
                        SharedUtil.putString(Contants.NICKNAME, userInfo.getNickName());
                        SharedUtil.putString(Contants.HEADER, userInfo.getHead());
                        SharedUtil.putString(Contants.SIGNATURE, userInfo.getSignature());
                        gotoNewAty(MatAct.class);
                    }
                    finish();
                } else {
                    Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusEvent(RegisterUser registerUser) {
        if (registerUser != null) {
            editUser.setText(registerUser.getUsername());
            editPwd.setText(registerUser.getPassword());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
