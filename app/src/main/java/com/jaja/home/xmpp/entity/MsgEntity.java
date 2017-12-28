package com.jaja.home.xmpp.entity;

import org.jivesoftware.smack.packet.Message;

import java.io.Serializable;

/**
 * Created by ${Terry} on 2017/12/23.
 */
public class MsgEntity extends Message implements Serializable {

    private String username;
    private String msg;
    private int mType;

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    /**
     * @param username
     * @param msg
     * @param type     0:from,1:to
     */

    public MsgEntity(String username, String msg, int type) {
        this.username = username;
        this.msg = msg;
        this.mType = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toXML() {
        return msg;
    }
}
