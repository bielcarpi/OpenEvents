package com.grup8.OpenEvents.model;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;
import com.grup8.OpenEvents.model.callbacks.GetMessagesCallback;
import com.grup8.OpenEvents.model.callbacks.GetUsersCallback;
import com.grup8.OpenEvents.model.callbacks.SuccessCallback;
import com.grup8.OpenEvents.model.entities.Message;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.CalendarHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class MessageModel {

    private static final MessageModel singleton = new MessageModel();
    private static final String POST_MESSAGE_URL = "/messages";
    private static final String GET_USERS_MESSAGING_URL = "/messages/users";

    private MessageModel(){}

    public static MessageModel getInstance(){
        return singleton;
    }


    public void sendMessage(String content, User receiver, SuccessCallback callback){
        final String bodyString = "{\"content\":\"" + content + "\",\"user_id_send\":\"" +
                UserModel.getInstance().getLoggedInUser().getId() + "\",\"user_id_recived\":\"" + receiver.getId() + "\"}";
        try{
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest(POST_MESSAGE_URL, RequestMethod.POST, body, true, new ResponseCallback() {
                @Override
                public void OnResponse(String strResponse) {
                    JSONObject response = null;
                    try {
                        response = new JSONObject(strResponse);
                        if(response.has("id"))
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


    public void getUsersMessaging(GetUsersCallback callback){
        UserModel.getInstance().getUsers(GET_USERS_MESSAGING_URL, callback); //Protected method
    }


    public void getMessages(User u, GetMessagesCallback callback){
        //Returns all the messages from the chat between the logged in user and the provided user
        ApiCommunicator.makeRequest(POST_MESSAGE_URL + "/" + u.getId(), RequestMethod.GET, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    Message[] messages = new Message[array.length()];
                    for(int i = 0; i < array.length(); i++){
                        JSONObject o = array.getJSONObject(i);

                        messages[i] = new Message(o.getInt("id"), o.getInt("user_id_send"),
                                o.getInt("user_id_recived"), o.getString("content"),
                                CalendarHelper.getCalendar(o.getString("timeStamp")));
                    }
                    callback.onResponse(true, messages);

                } catch (JSONException | ParseException e) {
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
