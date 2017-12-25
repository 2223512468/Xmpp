package com.jaja.home.xmpp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jaja.home.xmpp.entity.MsgEntity;
import com.jaja.home.xmpp.util.Contants;
import com.jaja.home.xmpp.util.SharedUtil;
import com.jaja.home.xmpp.util.StringUtil;
import com.jaja.home.xmpp.util.XmppConnectionUtils;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * Created by ${Terry} on 2017/12/22.
 */
public class CoreSerice extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                XmppConnectionUtils.openConnection();
                String username = SharedUtil.getString(Contants.USERNAME);
                String password = SharedUtil.getString(Contants.PASSWORD);
                if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
                    return;
                }
                XmppUtils.login(username, password);
                XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
                //消息监听
                // TaxiChatManagerListener chatManagerListener = new TaxiChatManagerListener();
                final Intent intent = new Intent();
                intent.setAction(Contants.MESACT);
                xmppConnection.getChatManager().addChatListener(new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean b) {
                        chat.addMessageListener(new MessageListener() {
                            @Override
                            public void processMessage(Chat chat, Message msg) {
                                String from = msg.getFrom();
                                String body = msg.getBody();
                                if (!StringUtil.isEmpty(body)) {
                                    MsgEntity msgEntity = new MsgEntity(StringUtil.splitStr(from), body, 0);
                                    intent.putExtra(Contants.MESSAGE, msgEntity);
                                    sendBroadcast(intent);
                                }
                            }
                        });
                    }
                });
                if (xmppConnection.isAuthenticated()) {
                    PacketFilter filter = new AndFilter(new PacketTypeFilter(Presence.class));
                    xmppConnection.addPacketListener(new PacketListener() {
                        @Override
                        public void processPacket(Packet packet) {
                            if (packet.getFrom().equals(packet.getTo())) {
                                return;
                            }
                            if (packet instanceof Presence) {
                                Presence presence = (Presence) packet;
                                String from = presence.getFrom();
                                String to = presence.getTo();
                                Intent intent = new Intent();
                                intent.setAction(Contants.LINE);
                                switch (presence.getType()) {
                                    case subscribe:
                                        Log.i("print", "来自" + from + "的好友申请");
                                        break;
                                    case subscribed:
                                        Log.i("print", "来自" + from + "的好友同意申请");
                                        break;
                                    case unsubscribed:
                                        Log.i("print", from + "的好友拒绝申请");
                                        break;
                                    case available:
                                        Log.i("print", from + "上线");
                                        intent.putExtra(Contants.ISONLINE, true);
                                        sendBroadcast(intent);
                                        break;
                                    case unavailable:
                                        Log.i("print", from + "下线");
                                        intent.putExtra(Contants.ISONLINE, false);
                                        sendBroadcast(intent);
                                        break;
                                }
                            }
                        }
                    }, filter);
                }
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
