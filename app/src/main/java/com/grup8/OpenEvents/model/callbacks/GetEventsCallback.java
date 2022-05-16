package com.grup8.OpenEvents.model.callbacks;

import com.grup8.OpenEvents.model.entities.Event;

public interface GetEventsCallback {
    void onResponse(boolean success, Event[] events);
}
