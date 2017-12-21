package com.jaja.home.xmpp.act;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaja.home.xmpp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${Terry} on 2017/12/21.
 */
public class AddUserAct extends BaseActivity {


    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.tv_input_number)
    TextView inputNum;
    @BindView(R.id.all_layout)
    LinearLayout allLayout;
    private int top;


    @Override
    protected int getViewId() {
        return R.layout.act_add_user;
    }

    @Override
    protected void initEvent() {

        mTabLayout.addTab(mTabLayout.newTab().setText("找人"));
        mTabLayout.addTab(mTabLayout.newTab().setText("找群"));
        mTabLayout.addTab(mTabLayout.newTab().setText("找公众号"));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TranslateAnimation trans = new TranslateAnimation(0, 0, -top, 0);
        trans.setDuration(1000);
        allLayout.startAnimation(trans);
    }

    @OnClick({R.id.tv_input_number})
    public void btnClick(View v) {
        top = inputNum.getTop();
        TranslateAnimation trans = new TranslateAnimation(0, 0, 0, -top);
        trans.setDuration(1000);
        //执行完成保存当前状态
        trans.setFillAfter(true);
        trans.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gotoNewAty(SearchAct.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        allLayout.startAnimation(trans);
    }


}
