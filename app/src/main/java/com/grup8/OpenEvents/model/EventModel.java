package com.grup8.OpenEvents.model;

import android.annotation.SuppressLint;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.utils.CalendarHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class EventModel {
    private static final EventModel singleton = new EventModel();

    private static final String BEST_EVENTS_REQUEST_URL = "/events/best";
    private EventModel(){}

    public static EventModel getInstance(){
        return singleton;
    }

    public interface EventCallback{
        void onResponse(boolean success, Event[] events);
    }



    public void getBestEvents(EventCallback callback){
        ApiCommunicator.makeRequest(BEST_EVENTS_REQUEST_URL, RequestMethod.GET, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(JSONObject response) {
                try {
                    JSONArray array = new JSONArray(response);
                    Event[] events = new Event[array.length()];
                    for(int i = 0; i < array.length(); i++){
                        JSONObject o = array.getJSONObject(i);

                        events[i] = new Event(o.getInt("id"), o.getString("name"), o.getInt("owner_id"),
                                CalendarHelper.getCalendar(o.getString("date")), o.getString("image"),
                                o.getString("location"), o.getString("description"),
                                CalendarHelper.getCalendar(o.getString("eventStart_date")),
                                CalendarHelper.getCalendar(o.getString("eventEnd_date")),
                                o.getInt("n_participators"), o.getString("slug"),
                                o.getString("type"), (float)o.getDouble("avg_score"));
                    }

                    callback.onResponse(true, events);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    callback.onResponse(false, null);
                }
            }
            @Override
            public void OnErrorResponse(String error) {
                callback.onResponse(false, null);
            }
        });
    }
}
