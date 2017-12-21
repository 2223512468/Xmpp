package com.jaja.home.xmpp.entity;

/**
 * Created by ${Terry} on 2017/12/19.
 * 当前用户信息实体类
 */
public class UserEntity {

    private String nickName;
    private String signature;
    private String head;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


}
