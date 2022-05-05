package com.grup8.OpenEvents.model.entities;

public class Assistance {
    private final int eventId;
    private final User assistant;
    private final float punctuation;
    private final String commentary;

    public Assistance(int eventId, User assistant, float puntuation, String commentary) {
        this.eventId = eventId;
        this.assistant = assistant;
        this.punctuation = puntuation;
        this.commentary = commentary;
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
}
