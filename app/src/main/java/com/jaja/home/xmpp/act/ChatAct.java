package com.jaja.home.xmpp.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.adapter.ChatAdapter;
import com.jaja.home.xmpp.adapter.EmotieAdapter;
import com.jaja.home.xmpp.base.BaseActivity;
import com.jaja.home.xmpp.entity.MsgEntity;
import com.jaja.home.xmpp.entity.UserEntity;
import com.jaja.home.xmpp.util.BiaoQinUtils;
import com.jaja.home.xmpp.util.Contants;
import com.jaja.home.xmpp.util.SharedUtil;
import com.jaja.home.xmpp.util.StringUtil;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;
import com.jaja.home.xmpp.widget.recyclerview.MultiRecycleView;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${Terry} on 2017/12/22.
 */
public class ChatAct extends BaseActivity implements GridView.OnItemClickListener {

    public static String USERNAME = "username";
    @BindView(R.id.chat_emotion)
    LinearLayout emotinChat;
    @BindView(R.id.et_input)
    EditText et;
    @BindView(R.id.recyclerView)
    MultiRecycleView mRecyclerView;
    private EmotieAdapter mAdapter;
    private UserEntity userEntity;
    private String username, headImg;
    private ChatAdapter adapter;
    private LocationBroadcastReceiver mLocationBroadcastReceiver = new LocationBroadcastReceiver();


    @Override
    protected int getViewId() {
        return R.layout.act_chat;
    }

    @Override
    protected void initEvent() {
        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.setLoadMoreable(false);
        userEntity = (UserEntity) getIntent().getSerializableExtra(USERNAME);
        username = SharedUtil.getString(Contants.USERNAME);
        headImg = SharedUtil.getString(Contants.HEADER);
        adapter = new ChatAdapter();
        mRecyclerView.setAdapter(adapter);
        loadDatas();
        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.MESACT);
        mContext.registerReceiver(mLocationBroadcastReceiver, filter);

    }


    private void loadDatas() {
        GridView g = new GridView(mContext);
        g.setNumColumns(7);
        g.setOnItemClickListener(this);
        try {
            String[] ps = getAssets().list("p");
            mAdapter = new EmotieAdapter(mContext, Arrays.asList(ps));
            g.setAdapter(mAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        emotinChat.addView(g);
    }


    @OnClick({R.id.chat_iv1, R.id.chat_iv2, R.id.chat_send_btn})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.chat_iv1:
                break;
            case R.id.chat_iv2:
                if (emotinChat.getVisibility() == View.GONE) {
                    emotinChat.setVisibility(View.VISIBLE);
                } else {
                    emotinChat.setVisibility(View.GONE);
                }
                break;
            case R.id.chat_send_btn:
                Log.i("print", userEntity.getUserName());
                Log.i("print", userEntity.getNickName());
                Chat friendChat = XmppUtils.getFriendChat(userEntity.getUserName(), null);
                String msg = et.getText().toString();
                if (StringUtil.isEmpty(msg)) {
                    Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    friendChat.sendMessage(msg);
                    MsgEntity msgEntity = new MsgEntity(username, msg, 1);
                    List<MsgEntity> toItems = new ArrayList<>();
                    toItems.add(msgEntity);
                    adapter.addItems(toItems);
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String pName = mAdapter.getItem(position);
        BiaoQinUtils.addBiaoQin(et, pName);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationBroadcastReceiver != null) {
            this.unregisterReceiver(mLocationBroadcastReceiver);
        }
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(Contants.MESACT)) return;
            MsgEntity msgEntity = (MsgEntity) intent.getSerializableExtra(Contants.MESSAGE);
            Log.i("print", "message" + msgEntity.toString());
            if (msgEntity != null) {
                List<MsgEntity> fromItems = new ArrayList<>();
                fromItems.add(msgEntity);
                adapter.addItems(fromItems);
            }
        }
    }

}
