package com.grup8.OpenEvents.model.calllbacks;

import com.grup8.OpenEvents.model.entities.User;

public interface GetUsersCallback {
    void onResponse(boolean success, User[] users);
}
