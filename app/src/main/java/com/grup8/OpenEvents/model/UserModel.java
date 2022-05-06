package com.grup8.OpenEvents.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;
import com.grup8.OpenEvents.model.api.RequestMethod;
import com.grup8.OpenEvents.model.api.ResponseCallback;
import com.grup8.OpenEvents.model.entities.User;

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
        void onResponse(boolean success, int errorMessage);
    }
    public interface GetUsersCallback{
        void onResponse(boolean success, User[] users);
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
                public void OnResponse(JSONObject response) {
                    System.out.println("Response!!");
                    System.out.println(LOGIN_REQUEST_URL);
                    System.out.println(body);
                    System.out.println("This is the response -->");
                    System.out.println(response);
                    System.out.println("End Response");
                    try {
                        addToken(response.getString("accessToken")); //Save access token
                        callback.onResponse(true, R.string.no_error);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onResponse(false, R.string.bad_response);
                    }
                }
                @Override
                public void OnErrorResponse(String error) {
                    System.out.println("Error Response!!");
                    System.out.println(LOGIN_REQUEST_URL);
                    System.out.println(body);
                    System.out.println("This is the error -->");
                    System.out.println(error);
                    System.out.println("End Response");
                    callback.onResponse(false, R.string.unreachable_server);
                }
            });
        }catch(JSONException e){
            callback.onResponse(false, R.string.internal_app_error);
        }
    }

    public void logOut(){
        deleteToken();
    }


    public void register(User user, LoginCallback callback){
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
                public void OnResponse(JSONObject response) {
                    if(response.has("name") && response.has("last_name") && response.has("email") && response.has("image"))
                        callback.onResponse(true, R.string.no_error);
                    else
                        callback.onResponse(false, R.string.bad_response);
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
