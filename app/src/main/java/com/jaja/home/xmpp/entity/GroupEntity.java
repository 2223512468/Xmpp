package com.jaja.home.xmpp.entity;

import java.util.List;

/**
 * Created by ${Terry} on 2017/12/21.
 */
public class GroupEntity {

    private String groupName;
    private List<UserEntity> friends;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<UserEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<UserEntity> friends) {
        this.friends = friends;
    }
}
