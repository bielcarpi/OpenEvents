package com.example.frames;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static EventManager sEventManager;
    private List<Event>  lEvents;

    public static EventManager getInstance(Context context) {
        if(sEventManager == null) {
            sEventManager = new EventManager(context);
        }
        return sEventManager;
    }

    private EventManager(Context context) {
        lEvents = new ArrayList<>();
        for( int i = 0; i < 100; i++) {
            Event e = new Event();
            e.setName("Event " + i);

            lEvents.add(e);
        }
    }

    public List<Event> getlEvents() {
        return lEvents;
    }

    public void setlEvents(List<Event> lEvents) {
        this.lEvents = lEvents;
    }
}


