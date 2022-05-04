package com.grup8.OpenEvents.model;

public class EventModel {
    private static final EventModel singleton = new EventModel();
    private EventModel(){}

    public static EventModel getInstance(){
        return singleton;
    }
}
