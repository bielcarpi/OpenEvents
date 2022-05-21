package com.grup8.OpenEvents.model;


import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;
import com.grup8.OpenEvents.model.callbacks.GetEventsCallback;
import com.grup8.OpenEvents.model.callbacks.SuccessCallback;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.CalendarHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventModel {
    private static final EventModel singleton = new EventModel();

    private static final String BEST_EVENTS_REQUEST_URL = "/events/best";
    private static final String ALL_EVENTS_REQUEST_URL = "/events";
    private static final String LOCATION_EVENT_SEARCH_URL = "/events/search?location=";
    private static final String KEYWORD_EVENT_SEARCH_URL = "/events/search?keyword=";
    private static final String DATE_EVENT_SEARCH_URL = "/events/search?date=";

    private EventModel(){}

    public static EventModel getInstance(){
        return singleton;
    }


    public void getAllEvents(GetEventsCallback callback){
        getEvents(ALL_EVENTS_REQUEST_URL, callback);
    }

    public void getBestEvents(GetEventsCallback callback){
        getEvents(BEST_EVENTS_REQUEST_URL, callback);
    }

    public void getCurrentUserEvents(User u, GetEventsCallback callback){
        getEvents("/users/" + u.getId() + "/events/current", callback);
    }

    public void getFutureUserEvents(User u, GetEventsCallback callback){
        getEvents("/users/" + u.getId() + "/events/future", callback);
    }

    public void getFinishedUserEvents(User u, GetEventsCallback callback){
        getEvents("/users/" + u.getId() + "/events/finished", callback);
    }

    public void searchEventsByLocation(String location, GetEventsCallback callback){
        getEvents(LOCATION_EVENT_SEARCH_URL + location, callback);
    }

    public void searchEventsByKeyword(String keyword, GetEventsCallback callback){
        getEvents(KEYWORD_EVENT_SEARCH_URL + keyword, callback);
    }

    public void searchEventsByDate(String date, GetEventsCallback callback){
        //TODO: Must control date format (it must be eg. 2022-12-01)
        getEvents(DATE_EVENT_SEARCH_URL + date, callback);
    }


    public void createEvent(Event event, SuccessCallback callback){

        final String bodyString = "{\"name\":\"" + event.getName() + "\",\"image\":\"" + event.getImage() + "\",\"location\":\"" + event.getLocation()
                + "\",\"description\":\"" + event.getDescription()  + "\",\"eventStart_date\":\"" + CalendarHelper.getString(event.getStartDate())
                + "\",\"eventEnd_date\":\"" + CalendarHelper.getString(event.getEndDate())  + "\",\"n_participators\":\"" + event.getParticipators() + "\",\"type\":\"" + event.getType() + "\"}";
        try{
            System.out.println(bodyString);
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest(ALL_EVENTS_REQUEST_URL, RequestMethod.POST, body, true, new ResponseCallback() {
                @Override
                public void OnResponse(String strResponse) {
                    try {
                        if(strResponse.equals(""))
                            callback.onResponse(true, R.string.no_error);
                        else{
                            JSONObject response = new JSONObject(strResponse);
                            if(response.has("affectedRows") && response.has("serverStatus"))
                                callback.onResponse(true, R.string.no_error);
                            else
                                callback.onResponse(false, R.string.bad_response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onResponse(false, R.string.bad_response);
                    }
                }
                @Override
                public void OnErrorResponse(String error) {
                    callback.onResponse(false, R.string.unreachable_server);
                }
            });
        }catch(JSONException e){
            callback.onResponse(false, R.string.internal_app_error);
        }
    }

    public void deleteEvent(Event event, SuccessCallback callback){
        ApiCommunicator.makeRequest("/events/" + event.getId(), RequestMethod.DELETE, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String strResponse) {
                try {
                    JSONObject response = new JSONObject(strResponse);
                    if (response.has("Mensaje"))
                        callback.onResponse(true, R.string.no_error);
                    else
                        callback.onResponse(false, R.string.bad_response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onResponse(false, R.string.bad_response);
                }
            }
            @Override
            public void OnErrorResponse(String error) {
                callback.onResponse(false, R.string.unreachable_server);
            }
        });
    }

    public void updateEvent(Event event, SuccessCallback callback){
        final String bodyString = "{\"name\":\"" + event.getName() + "\",\"image\":\"" + event.getImage() + "\",\"location\":\"" + event.getLocation()
                + "\",\"description\":\"" + event.getDescription()  + "\",\"eventStart_date\":\"" + CalendarHelper.getString(event.getStartDate())
                + "\",\"eventEnd_date\":\"" + CalendarHelper.getString(event.getEndDate())  + "\",\"n_participators\":\"" + event.getParticipators() + "\",\"type\":\"" + event.getType() + "\"}";
        try{
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest("/events/" + event.getId(), RequestMethod.PUT, body, true, new ResponseCallback() {
                @Override
                public void OnResponse(String strResponse) {
                    try {
                        JSONObject response = new JSONObject(strResponse);
                        if (response.has("name") && response.has("image"))
                            callback.onResponse(true, R.string.no_error);
                        else
                            callback.onResponse(false, R.string.bad_response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onResponse(false, R.string.bad_response);
                    }
                }
                @Override
                public void OnErrorResponse(String error) {
                    callback.onResponse(false, R.string.unreachable_server);
                }
            });
        }catch(JSONException e){
            e.printStackTrace();
            callback.onResponse(false, R.string.internal_app_error);
        }
    }


    protected void getEvents(String url, GetEventsCallback callback){
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
                                o.getString("type"));
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
