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

    public static String getAveragePunctuation(Assistance[] eventAssistances) {
        int totalPunctuation = 0;
        int assistantsThatGavePunctuation = 0;
        for(Assistance a: eventAssistances){
            if(a.punctuation >= 0){
                totalPunctuation += a.punctuation;
                assistantsThatGavePunctuation++;
            }
        }

        if(assistantsThatGavePunctuation == 0) return "-";
        return Integer.toString(totalPunctuation/assistantsThatGavePunctuation);
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
