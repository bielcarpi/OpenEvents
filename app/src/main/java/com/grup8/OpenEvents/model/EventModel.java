package com.grup8.OpenEvents.model;


import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.CalendarHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class EventModel {
    private static final EventModel singleton = new EventModel();

    private static final String BEST_EVENTS_REQUEST_URL = "/events/best";
    private static final String ALL_EVENTS_REQUEST_URL = "/events";
    private EventModel(){}

    public static EventModel getInstance(){
        return singleton;
    }

    public interface EventCallback{
        void onResponse(boolean success, Event[] events);
    }



    public void getAllEvents(EventCallback callback){
        getEvents(ALL_EVENTS_REQUEST_URL, callback);
    }

    public void getBestEvents(EventCallback callback){
        getEvents(BEST_EVENTS_REQUEST_URL, callback);
    }

    public void getCurrentUserEvents(EventCallback callback){
        getEvents("/users/" + UserModel.getInstance().getLoggedInUser().getId() + "/events/current", callback);
    }

    public void getFutureUserEvents(EventCallback callback){
        getEvents("/users/" + UserModel.getInstance().getLoggedInUser().getId() + "/events/future", callback);
    }

    public void getFinishedUserEvents(EventCallback callback){
        getEvents("/users/" + UserModel.getInstance().getLoggedInUser().getId() + "/events/finished", callback);
    }


    private void getEvents(String url, EventCallback callback){
        ApiCommunicator.makeRequest(url, RequestMethod.GET, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String response) {
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
                                o.getString("type"), o.getString("avg_score").equals("null")? -1.0f : (float)o.getDouble("avg_score"));
                    }

                    callback.onResponse(true, events);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    callback.onResponse(false, null);
                }
            }
            @Override
            public void OnErrorResponse(String error) {
                System.out.println(error);
                callback.onResponse(false, null);
            }
        });
    }
}
