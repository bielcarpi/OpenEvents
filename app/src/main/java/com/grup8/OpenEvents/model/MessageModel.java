package com.grup8.OpenEvents.model;

public class MessageModel {

    private static final MessageModel singleton = new MessageModel();
    private MessageModel(){}

    public static MessageModel getInstance(){
        return singleton;
    }
}
