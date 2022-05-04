package com.grup8.OpenEvents.model;

public class UserModel {

    private static final UserModel singleton = new UserModel();
    private UserModel(){}

    public static UserModel getInstance(){
        return singleton;
    }
}
