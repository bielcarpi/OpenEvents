package com.grup8.OpenEvents.model.entities;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager sUserManager;
    private List<User> lUser;

    public static UserManager getInstance(Context context) {
        if(sUserManager == null) {
            sUserManager = new UserManager(context);
        }
        return sUserManager;
    }

    private UserManager(Context context) {
        lUser = new ArrayList<>();

    }

    public List<User> getlUsers() {
        return lUser;
    }

    public void setlUser(List<User> lUsers) {

        this.lUser = lUsers;
    }
}
