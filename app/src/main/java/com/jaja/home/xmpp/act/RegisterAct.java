package com.jaja.home.xmpp.act;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.base.BaseActivity;
import com.jaja.home.xmpp.entity.RegisterUser;
import com.jaja.home.xmpp.util.StringUtil;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/19.
 */
public class RegisterAct extends BaseActivity {

    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.edit_pwd)
    EditText editPwd;


    @Override
    protected int getViewId() {
        return R.layout.act_register;
    }

    @Override
    protected void initEvent() {


    }


    @OnClick({R.id.tv_register})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                String username = editUser.getText().toString();
                String password = editPwd.getText().toString();
                if (StringUtil.isEmpty(username)) {
                    Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(password)) {
                    Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                int code = XmppUtils.registerUser(username, password);
                switch (code) {
                    case 0:
                        Toast.makeText(mContext, "服务器无响应", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                        RegisterUser registerUser = new RegisterUser(username, password);
                        EventBus.getDefault().post(registerUser);
                        finish();
                        break;
                    case 2:
                        Toast.makeText(mContext, "用户名已存在", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
                        break;
                }

                break;
        }
    }

}
