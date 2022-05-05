package com.grup8.OpenEvents.model.entities;

import java.util.Calendar;

public class Message {
    private int messageId;
    private int senderId;
    private int receiverId;
    private String content;
    private Calendar timestamp;

    public Message(int messageId, int senderId, int receiverId, String content, Calendar timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }
    public int getSenderId() {
        return senderId;
    }
    public int getReceiverId() {
        return receiverId;
    }
    public String getContent() {
        return content;
    }
    public Calendar getTimestamp() {
        return timestamp;
    }
}
