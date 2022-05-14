package com.grup8.OpenEvents.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;
import com.grup8.OpenEvents.model.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {

    private static final UserModel singleton = new UserModel();
    private static final String TOKEN_KEY = "token";
    private static final String LOGIN_REQUEST_URL = "/users/login";
    private static final String REGISTER_REQUEST_URL = "/users";
    private static final String SEARCH_USER_URL = "/users/search?s=";
    private static final String GET_FRIENDS_URL = "/friends";
    private static final String UPDATE_USER_URL = "/users";

    private final SharedPreferences spToken;
    private String token;
    private User loggedInUser;


    public static UserModel getInstance(){
        return singleton;
    }


    public interface SuccessCallback {
        void onResponse(boolean success, int errorMessage);
    }
    public interface GetUsersCallback{
        void onResponse(boolean success, User[] users);
    }
    public interface GetUserCallback{
        void onResponse(boolean success, User user);
    }


    private UserModel(){
        Context c = OpenEventsApplication.getAppContext();
        spToken = c.getSharedPreferences(c.getString(R.string.token_preference_file), Context.MODE_PRIVATE);

        token = spToken.getString(TOKEN_KEY, null);
        ApiCommunicator.setToken(token);
    }


    public boolean userLoggedIn(){
        return token != null && loggedInUser != null; //If there is no token, user is not logged in
    }


    public void logIn(String email, String password, SuccessCallback callback){
        if(!validateEmail(email)){
            callback.onResponse(false, R.string.incorrect_email_format);
            return;
        }
        else if(!validatePassword(password)){
            callback.onResponse(false, R.string.incorrect_password_format);
            return;
        }

        final String bodyString = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        try{
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest(LOGIN_REQUEST_URL, RequestMethod.POST, body, new ResponseCallback() {
                @Override
                public void OnResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        addToken(jsonResponse.getString("accessToken")); //Save access token

                        //Now that we know that the login is correct, we must make requests
                        // to retrieve all the information of the User (name, photo, etc.)
                        ApiCommunicator.makeRequest(SEARCH_USER_URL + email, RequestMethod.GET, null, true, new ResponseCallback() {
                            @Override
                            public void OnResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONArray(response).getJSONObject(0);
                                    int id = jsonResponse.getInt("id");
                                    String name = jsonResponse.getString("name");
                                    String lastName = jsonResponse.getString("last_name");
                                    String image = jsonResponse.getString("image");

                                    loggedInUser = new User(id, name, lastName, email, image);
                                    callback.onResponse(true, -1);
                                } catch (Exception e) {
                                    callback.onResponse(false, R.string.bad_response);
                                }
                            }
                            @Override
                            public void OnErrorResponse(String error) {
                                callback.onResponse(false, R.string.bad_response);
                            }
                        });
                    }catch (JSONException e) {
                        callback.onResponse(false, R.string.bad_response);
                    }
                }
                @Override
                public void OnErrorResponse(String error) {
                    callback.onResponse(false, R.string.incorrect_login);
                }
            });
        }catch(JSONException e){
            callback.onResponse(false, R.string.internal_app_error);
        }
    }

    public void logOut(){
        loggedInUser = null;
        deleteToken();
    }


    public void register(User user, SuccessCallback callback){
        if(!validateName(user.getName()) || !validateName(user.getLastName())){
            callback.onResponse(false, R.string.incorrect_name_format);
            return;
        }
        else if(!validateEmail(user.getEmail())){
            callback.onResponse(false, R.string.incorrect_email_format);
            return;
        }
        else if(!validatePassword(user.getPassword())){
            callback.onResponse(false, R.string.incorrect_password_format);
            return;
        }
        else if(!validateUserImage(user.getImage())){
            callback.onResponse(false, R.string.incorrect_user_image_format);
            return;
        }

        final String bodyString = "{\"name\":\"" + user.getName() + "\",\"last_name\":\"" + user.getLastName() + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"" + user.getPassword() + "\",\"image\":\"" + user.getImage() + "\"}";
        try{
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest(REGISTER_REQUEST_URL, RequestMethod.POST, body, new ResponseCallback() {
                @Override
                public void OnResponse(String strResponse) {
                    JSONObject response = null;
                    try {
                        response = new JSONObject(strResponse);
                        if(response.has("name") && response.has("last_name") && response.has("email") && response.has("image"))
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


    public void updateUserStats(GetUserCallback callback){
        //Needs to make three requests concatenated (first, the statistics, then the number of events, and finally the number of friends)
        ApiCommunicator.makeRequest("/users/" + loggedInUser.getId() + "/statistics", RequestMethod.GET, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    float avgScore = (float)jsonResponse.getDouble("avg_score");
                    int numComments = jsonResponse.getInt("num_comments");
                    float percentageCommentersBelow = (float)jsonResponse.getDouble("percentage_commenters_below");
                    ApiCommunicator.makeRequest("/users/" + loggedInUser.getId() + "events", RequestMethod.GET, null, true, new ResponseCallback() {
                        @Override
                        public void OnResponse(String response) {
                            try {
                                JSONArray jsonResponse = new JSONArray(response);
                                int numEvents = jsonResponse.length();

                                ApiCommunicator.makeRequest(GET_FRIENDS_URL, RequestMethod.GET, null, true, new ResponseCallback() {
                                    @Override
                                    public void OnResponse(String response) {
                                        try {
                                            JSONArray jsonResponse = new JSONArray(response);
                                            int numFriends = jsonResponse.length();
                                            loggedInUser.updateStats(avgScore, numComments, percentageCommentersBelow, numEvents, numFriends);
                                            callback.onResponse(true, loggedInUser);
                                        } catch (JSONException e) {
                                            callback.onResponse(false, null);
                                        }
                                    }
                                    @Override
                                    public void OnErrorResponse(String error) {
                                        callback.onResponse(false, null);
                                    }
                                });
                            } catch (JSONException e) {
                                callback.onResponse(false, null);
                            }
                        }
                        @Override
                        public void OnErrorResponse(String error) {
                            callback.onResponse(false, null);
                        }
                    });
                } catch (JSONException e) {
                    callback.onResponse(false, null);
                }
            }
            @Override
            public void OnErrorResponse(String error) {
                callback.onResponse(false, null);
            }
        });
    }


    public void getUserFriends(GetUsersCallback callback){
        ApiCommunicator.makeRequest(GET_FRIENDS_URL, RequestMethod.GET, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    User[] friends = new User[array.length()];
                    for(int i = 0; i < array.length(); i++){
                        JSONObject o = array.getJSONObject(i);

                        friends[i] = new User(o.getInt("id"), o.getString("name"), o.getString("last_name"),
                                o.getString("email"), o.getString("image"));
                    }
                    callback.onResponse(true, friends);

                } catch (JSONException e) {
                    callback.onResponse(false, null);
                }
            }
            @Override
            public void OnErrorResponse(String error) {
                callback.onResponse(false, null);
            }
        });
    }


    public void searchUser(String search, GetUsersCallback callback){
        ApiCommunicator.makeRequest(SEARCH_USER_URL + search, RequestMethod.GET, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    User[] friends = new User[array.length()];
                    for(int i = 0; i < array.length(); i++){
                        JSONObject o = array.getJSONObject(i);

                        friends[i] = new User(o.getInt("id"), o.getString("name"), o.getString("last_name"),
                                o.getString("email"), o.getString("image"));
                    }
                    callback.onResponse(true, friends);

                } catch (JSONException e) {
                    callback.onResponse(false, null);
                }
            }
            @Override
            public void OnErrorResponse(String error) {
                callback.onResponse(false, null);
            }
        });
    }


    public void updateUser(User newUser, SuccessCallback callback){
        final String bodyString = "{\"name\":\"" + newUser.getName() + "\",\"last_name\":\"" + newUser.getLastName() + "\",\"email\":\"" + newUser.getEmail() + "\",\"password\":\"" + newUser.getPassword() + "\",\"image\":\"" + newUser.getImage() + "\"}";
        ApiCommunicator.makeRequest(UPDATE_USER_URL, RequestMethod.PUT, null, true, new ResponseCallback() {
            @Override
            public void OnResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.has("email")){ //Success
                        loggedInUser.updateInfo(newUser.getName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getImage());
                        callback.onResponse(true, -1);
                    }
                    else{
                        callback.onResponse(true, R.string.bad_response);
                    }
                } catch (JSONException e) {
                    callback.onResponse(false, R.string.bad_response);
                }
            }
            @Override
            public void OnErrorResponse(String error) {
                callback.onResponse(false, R.string.internal_app_error);
            }
        });
    }



    public User getLoggedInUser(){
        return loggedInUser;
    }



    private void deleteToken(){
        SharedPreferences.Editor spTokenEditor = spToken.edit();
        spTokenEditor.remove(TOKEN_KEY);
        spTokenEditor.apply();

        this.token = null;
        ApiCommunicator.deleteToken();
    }
    private void addToken(String token){
        SharedPreferences.Editor spTokenEditor = spToken.edit();
        spTokenEditor.putString(TOKEN_KEY, token);
        spTokenEditor.apply();

        this.token = token;
        ApiCommunicator.setToken(token);
    }


    private boolean validateName(String name){
        if(name == null) return false;
        return name.length() >= 2 && name.length() <= 12;
    }
    private boolean validateEmail(String email){
        if(email == null) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean validatePassword(String password){
        if(password == null) return false;
        //Uppercase, lowercase, numbers & 8 characters minimum
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
    private boolean validateUserImage(String userImageURL){
        if(userImageURL == null) return false;
        return Patterns.WEB_URL.matcher(userImageURL).matches();
    }
}
