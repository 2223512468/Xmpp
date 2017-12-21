package com.jaja.home.xmpp.util.xmpp;

import android.util.Log;

import com.jaja.home.xmpp.entity.GroupEntity;
import com.jaja.home.xmpp.entity.UserEntity;
import com.jaja.home.xmpp.util.Contants;
import com.jaja.home.xmpp.util.StringUtil;
import com.jaja.home.xmpp.util.XmppConnectionUtils;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.search.UserSearchManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ${Terry} on 2017/12/19.
 */
public class XmppUtils {


    /**
     * @param username
     * @param password
     * @return 0:服务器没有响应
     * 1:注册成功
     * 2:帐户已经存在
     * 3:其它错误
     * <p/>
     * 该方法可以在主线程中调用
     */

    public static int registerUser(String username, String password) {
        XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isConnected()) {
            Registration req = new Registration();
            req.setType(IQ.Type.SET);
            req.setTo(xmppConnection.getServiceName());
            req.setUsername(username);
            req.setPassword(password);
            req.addAttribute("access", username);
            PacketFilter filter = new AndFilter(new PacketIDFilter(req.getPacketID()), new PacketTypeFilter(IQ.class));
            PacketCollector conllector = xmppConnection.createPacketCollector(filter);
            xmppConnection.sendPacket(req);
            IQ result = (IQ) conllector.nextResult(SmackConfiguration.getPacketReplyTimeout());
            conllector.cancel();
            if (result == null) {
                return 0;
            } else if (result.getType() == IQ.Type.RESULT) {
                return 1;
            } else {
                if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
                    return 2;
                } else {
                    return 3;
                }
            }
        }
        return 3;
    }

    /**
     * 登录操作
     *
     * @param username
     * @param password
     * @return
     */

    public static boolean login(String username, String password) {
        XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isConnected()) {
            if (xmppConnection.isAuthenticated()) {
                //已经登录
                return true;
            }
            try {
                xmppConnection.login(username, password);
                if (xmppConnection.isAuthenticated()) {
                    //设置登录状态为在线
                    Presence presence = new Presence(Presence.Type.available);
                    xmppConnection.sendPacket(presence);
                    return true;
                }

            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserEntity getUserInfo(String user) {
        XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isAuthenticated()) {
            VCard vCard = new VCard();
            try {
                if (StringUtil.isEmpty(user)) {
                    vCard.load(xmppConnection);
                } else {
                    vCard.load(xmppConnection, user + "@" + xmppConnection.getServiceName());
                }
                String nickName = vCard.getNickName();
                if (StringUtil.isEmpty(nickName)) {
                    return null;
                } else {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setNickName(nickName);
                    userEntity.setSignature(vCard.getField(Contants.SIGNATURE));
                    userEntity.setHead(vCard.getField(Contants.HEADER));
                    userEntity.setUserName(user);
                    return userEntity;
                }
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static UserEntity getUserInfo() {
        return getUserInfo(null);
    }


    /**
     * 查询用户
     *
     * @param userName
     * @return
     * @throws XMPPException
     */
    public static List<UserEntity> searchUsers(String userName) {
        List<UserEntity> userList = new ArrayList<>();
        XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isAuthenticated()) {
            UserSearchManager search = new UserSearchManager(xmppConnection);
            try {
                Form searchForm = search.getSearchForm("search." + xmppConnection.getServiceName());
                Form answerForm = searchForm.createAnswerForm();
                answerForm.setAnswer("Username", true);
                answerForm.setAnswer("search", userName);

                ReportedData data = search.getSearchResults(answerForm, "search." + xmppConnection.getServiceName());
                Iterator<ReportedData.Row> it = data.getRows();
                while (it.hasNext()) {
                    ReportedData.Row row = it.next();
                    String searchResult = row.getValues("Username").next().toString();
                    UserEntity userInfo = getUserInfo(searchResult);
                    Log.i("print", searchResult);
                    userList.add(userInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userList;
        }
        return null;
    }


    /**
     * 保存用户信息
     *
     * @param userEntity
     */
    public static Boolean saveUserInfo(UserEntity userEntity) {
        XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isAuthenticated()) {
            try {
                VCard vCard = new VCard();
                vCard.setNickName(userEntity.getNickName());
                vCard.setField(Contants.SIGNATURE, userEntity.getSignature());
                vCard.setField(Contants.HEADER, userEntity.getHead());
                vCard.save(xmppConnection);
                return true;
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param group  添加到组的名称
     * @param friend 添加的好友名称
     */

    public static Boolean addGroupFriend(String group, String friend) {
        XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isAuthenticated()) {
            Roster roster = xmppConnection.getRoster();
            try {
                roster.createEntry(friend + "@" + xmppConnection.getServiceName(), friend, new String[]{group});
                return true;
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<GroupEntity> queryFriendGroup() {
        List<GroupEntity> groupList = new ArrayList<>();
        XMPPConnection xmppConnection = XmppConnectionUtils.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isAuthenticated()) {
            Roster roster = xmppConnection.getRoster();//获得当前用户对象
            Collection<RosterGroup> groups = roster.getGroups();//获得当前用户所有组信息
            for (RosterGroup rg : groups) {
                GroupEntity groupEntity = new GroupEntity();//创建组对象
                groupEntity.setGroupName(rg.getName());//添加组名称
                //遍历该组下所有好友
                List<UserEntity> friendsList = new ArrayList<>();
                Collection<RosterEntry> entries = rg.getEntries();
                for (RosterEntry re : entries) {
                    UserEntity friends = getUserInfo(re.getName());
                    friendsList.add(friends);
                }
                groupEntity.setFriends(friendsList);
                groupList.add(groupEntity);
            }
        }
        return groupList;
    }
}
