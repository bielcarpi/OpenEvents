package com.grup8.OpenEvents.model.callbacks;

import com.grup8.OpenEvents.model.entities.User;

public interface GetUserCallback {
    void onResponse(boolean success, User user);
}
