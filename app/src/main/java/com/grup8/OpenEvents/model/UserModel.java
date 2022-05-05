package com.grup8.OpenEvents.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {

    private static final UserModel singleton = new UserModel();
    private static final String TOKEN_KEY = "token";
    private static final String LOGIN_REQUEST_URL = "/users/login";
    private static final String REGISTER_REQUEST_URL = "/users";

    private final SharedPreferences spToken;
    private String token;


    public static UserModel getInstance(){
        return singleton;
    }


    public interface LoginCallback{
        void OnResponse(boolean success, int errorMessage);
    }
    public interface RegisterCallback{
        void OnResponse(boolean success, int errorMessage);
    }


    private UserModel(){
        Context c = OpenEventsApplication.getAppContext();
        spToken = c.getSharedPreferences(c.getString(R.string.token_preference_file), Context.MODE_PRIVATE);

        token = spToken.getString(TOKEN_KEY, null);
    }


    public boolean userLoggedIn(){
        return token != null; //If there is no token, user is not logged in
    }


    public void logIn(String email, String password, LoginCallback callback){
        if(!validateEmail(email)){
            callback.OnResponse(false, R.string.incorrect_email_format);
            return;
        }
        else if(!validatePassword(password)){
            callback.OnResponse(false, R.string.incorrect_password_format);
            return;
        }

        final String bodyString = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        try{
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest(LOGIN_REQUEST_URL, RequestMethod.POST, body, new ResponseCallback() {
                @Override
                public void OnResponse(JSONObject response) {
                    try {
                        addToken(response.getString("accessToken")); //Save access token
                        callback.OnResponse(true, R.string.no_error);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.OnResponse(false, R.string.bad_response);
                    }
                }
                @Override
                public void OnErrorResponse(String error) {
                    callback.OnResponse(false, R.string.unreachable_server);
                }
            });
        }catch(JSONException e){
            callback.OnResponse(false, R.string.internal_app_error);
        }
    }

    public void logOut(){
        deleteToken();
    }


    public void register(String name, String lastName, String email, String password, String imageURL, RegisterCallback callback){
        if(!validateName(name) || !validateName(lastName)){
            callback.OnResponse(false, R.string.incorrect_name_format);
            return;
        }
        else if(!validateEmail(email)){
            callback.OnResponse(false, R.string.incorrect_email_format);
            return;
        }
        else if(!validatePassword(password)){
            callback.OnResponse(false, R.string.incorrect_password_format);
            return;
        }
        else if(!validateUserImage(imageURL)){
            callback.OnResponse(false, R.string.incorrect_user_image_format);
            return;
        }

        final String bodyString = "{\"name\":\"" + name + "\",\"last_name\":\"" + lastName + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"image\":\"" + imageURL + "\"}";
        try{
            JSONObject body = new JSONObject(bodyString);
            ApiCommunicator.makeRequest(REGISTER_REQUEST_URL, RequestMethod.POST, body, new ResponseCallback() {
                @Override
                public void OnResponse(JSONObject response) {
                    if(response.has("name") && response.has("last_name") && response.has("email") && response.has("image"))
                        callback.OnResponse(true, R.string.no_error);
                    else
                        callback.OnResponse(false, R.string.bad_response);
                }
                @Override
                public void OnErrorResponse(String error) {
                    callback.OnResponse(false, R.string.unreachable_server);
                }
            });
        }catch(JSONException e){
            callback.OnResponse(false, R.string.internal_app_error);
        }
    }



    private void deleteToken(){
        SharedPreferences.Editor spTokenEditor = spToken.edit();
        spTokenEditor.remove(TOKEN_KEY);
        spTokenEditor.apply();

        this.token = null;
    }
    private void addToken(String token){
        SharedPreferences.Editor spTokenEditor = spToken.edit();
        spTokenEditor.putString(TOKEN_KEY, token);
        spTokenEditor.apply();

        this.token = token;
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