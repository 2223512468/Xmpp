package com.jaja.home.xmpp.act;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.frag.ContantFrag;
import com.jaja.home.xmpp.frag.MsgFragment;
import com.jaja.home.xmpp.frag.SetFragment;
import com.jaja.home.xmpp.util.Contants;
import com.jaja.home.xmpp.util.SharedUtil;
import com.jaja.home.xmpp.widget.commontablayout.CommonTabLayout;
import com.jaja.home.xmpp.widget.commontablayout.CustomTabEntity;
import com.jaja.home.xmpp.widget.commontablayout.OnTabSelectListener;
import com.jaja.home.xmpp.widget.commontablayout.TabEntity;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MatAct extends BaseActivity {

    @BindView(R.id.headImv)
    CircleImageView headImv;
    @BindView(R.id.fLayout)
    FrameLayout frameLayout;
    @BindView(R.id.tablayout_bottom)
    CommonTabLayout tabLayout;
    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.add)
    TextView addTv;


    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {

        String header = SharedUtil.getString(Contants.HEADER);
        try {
            Bitmap headBit = BitmapFactory.decodeStream(getAssets().open("head/" + header));
            headImv.setImageBitmap(headBit);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initFragment();

    }


    @OnClick({R.id.add})
    public void addUser(View v) {
        switch (v.getId()) {
            case R.id.add:
                gotoNewAty(AddUserAct.class);
                break;
        }
    }


    private void initFragment() {
        tabLayout.setTabData(genTabEntity(), getSupportFragmentManager(), R.id.fLayout, getFragments());
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        addTv.setVisibility(View.GONE);
                        titleName.setText("消息");
                        break;
                    case 1:
                        addTv.setVisibility(View.VISIBLE);
                        titleName.setText("联系人");
                        break;
                    case 2:
                        addTv.setVisibility(View.GONE);
                        titleName.setText("动态");
                        break;
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new MsgFragment());
        list.add(new ContantFrag());
        list.add(new SetFragment());
        return list;
    }

    private ArrayList<CustomTabEntity> genTabEntity() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        int[] mIconUnselectIds = {
                R.drawable.em_conversation_normal, R.drawable.em_contact_list_normal,
                R.drawable.em_settings_normal};
        int[] mIconSelectIds = {
                R.drawable.em_conversation_selected, R.drawable.em_contact_list_selected,
                R.drawable.em_settings_selected};
        for (int i = 0; i < mIconUnselectIds.length; i++) {
            mTabEntities.add(new TabEntity("", mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return mTabEntities;
    }

}
