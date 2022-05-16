package com.grup8.OpenEvents.model.calllbacks;

import com.grup8.OpenEvents.model.entities.Message;

public interface GetMessagesCallback {
    void onResponse(boolean success, Message[] messages);
}
