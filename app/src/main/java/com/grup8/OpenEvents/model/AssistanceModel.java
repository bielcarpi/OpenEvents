package com.grup8.OpenEvents.model;


import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;
import com.grup8.OpenEvents.model.callbacks.GetAssistancesCallback;
import com.grup8.OpenEvents.model.callbacks.GetEventsCallback;
import com.grup8.OpenEvents.model.callbacks.SuccessCallback;
import com.grup8.OpenEvents.model.entities.Assistance;
import com.grup8.OpenEvents.model.entities.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AssistanceModel {

    private static final AssistanceModel singleton = new AssistanceModel();

    private AssistanceModel(){}

    public static AssistanceModel getInstance(){
        return singleton;
    }


    public void getFutureUserAssistances(GetEventsCallback callback){
        EventModel.getInstance().getEvents("/users/" + UserModel.getInstance().getLoggedInUser().getId() + "/assistances/future", callback);
    }


    public void getFinishedUserAssistances(GetEventsCallback callback){
        EventModel.getInstance().getEvents("/users/" + UserModel.getInstance().getLoggedInUser().getId() + "/assistances/finished", callback);
    }


    public void getAssistancesFromEvent(Event event, GetAssistancesCallback callback){
        ApiCommunicator.makeRequest("/users/" + event.getOwnerId() + "/" + event.getId(), RequestMethod.GET, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    Assistance[] assistances = new Assistance[array.length()];
                    for(int i = 0; i < array.length(); i++){
                        JSONObject o = array.getJSONObject(i);

                        //Once the assistance is created, it will internally load the User, given the email
                        assistances[i] = new Assistance(event.getId(), o.getString("email"),
                                o.getString("puntuation").equals("null")? -1: o.getInt("puntuation"),
                                o.getString("comentary").equals("null")? null: o.getString("comentary"));
                    }

                    callback.onResponse(true, null);
                } catch (JSONException e) {
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


    public void newAssistance(Event event, SuccessCallback callback){
        ApiCommunicator.makeRequest("/events/" + event.getId(), RequestMethod.POST, null, new ResponseCallback() {
            @Override
            public void OnResponse(String strResponse) {
                try {
                    JSONObject response = new JSONObject(strResponse);
                    if(response.has("affectedRows") && response.has("serverStatus"))
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


    public void removeAssistance(Event event, SuccessCallback callback){
        ApiCommunicator.makeRequest("/events/" + event.getId() + "/assistances", RequestMethod.DELETE, null, new ResponseCallback() {
            @Override
            public void OnResponse(String strResponse) {
                try {
                    JSONObject response = new JSONObject(strResponse);
                    if(response.has("affectedRows") && response.has("serverStatus"))
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

    public void updateAssistance(Event event, int newPunctuation, String newCommentary, SuccessCallback callback){
        final String bodyString = "{\"puntuation\":\"" + newPunctuation + "\",\"comentary\":\"" + newCommentary + "\"}";
        try{
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest("/events/" + event.getId() + "/assistances", RequestMethod.PUT, body, new ResponseCallback() {
                @Override
                public void OnResponse(String strResponse) {
                    try {
                        JSONObject response = new JSONObject(strResponse);
                        if(response.has("affectedRows") && response.has("serverStatus"))
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
            callback.onResponse(false, R.string.internal_app_error);
        }
    }


}
