package com.grup8.OpenEvents.model.entities;

import com.grup8.OpenEvents.model.UserModel;

import java.io.Serializable;

public class Assistance implements Serializable {
    private final int eventId;
    private User assistant;
    private final float punctuation;
    private final String commentary;

    public Assistance(int eventId, String userEmail, float puntuation, String commentary) {
        this.eventId = eventId;
        this.punctuation = puntuation;
        this.commentary = commentary;

        UserModel.getInstance().searchUser(userEmail, (success, users) -> {
            if(success) setAssistant(users[0]);
        });
    }


    public int getEventId() {
        return eventId;
    }
    public User getAssistant() {
        return assistant;
    }
    public float getPunctuation() {
        return punctuation;
    }
    public String getCommentary() {
        return commentary;
    }

    protected void setAssistant(User assistant){
        this.assistant = assistant;
    }
}
