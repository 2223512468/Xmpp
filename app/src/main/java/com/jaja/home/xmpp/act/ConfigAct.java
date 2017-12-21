package com.jaja.home.xmpp.act;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.entity.UserEntity;
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
 * Created by ${Terry} on 2017/12/19.
 */
public class ConfigAct extends BaseActivity {

    @BindView(R.id.header)
    CircleImageView circleImageView;
    @BindView(R.id.edit_user_nickName)
    EditText edit_user_nickName;
    @BindView(R.id.edit_user_sign)
    EditText edit_user_sign;

    private String headStr;


    @Override
    protected int getViewId() {
        return R.layout.act_config;
    }

    @Override
    protected void initEvent() {
        EventBus.getDefault().register(this);
    }


    @OnClick({R.id.headChange, R.id.ok})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.headChange:
                gotoNewAty(HeadSelectAct.class);
                break;
            case R.id.ok:
                String username = edit_user_nickName.getText().toString();
                String editsign = edit_user_sign.getText().toString();
                if (StringUtil.isEmpty(username)) {
                    Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(editsign)) {
                    Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (this.headStr == null) {
                    Toast.makeText(mContext, "请选择头像", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserEntity userEntity = new UserEntity();
                userEntity.setNickName(username);
                userEntity.setSignature(editsign);
                userEntity.setHead(headStr);
                Boolean flag = XmppUtils.saveUserInfo(userEntity);
                if (flag) {
                    SharedUtil.putString(Contants.NICKNAME, userEntity.getNickName());
                    SharedUtil.putString(Contants.HEADER, userEntity.getHead());
                    SharedUtil.putString(Contants.SIGNATURE, userEntity.getSignature());
                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String str) {
        this.headStr = str;
        try {
            Bitmap headBit = BitmapFactory.decodeStream(getAssets().open("head/" + str));
            circleImageView.setImageBitmap(headBit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
