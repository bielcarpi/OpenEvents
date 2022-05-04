package com.grup8.OpenEvents.model;

public class FriendModel {

    private static final FriendModel singleton = new FriendModel();
    private FriendModel(){}

    public static FriendModel getInstance(){
        return singleton;
    }
}
