package com.grup8.OpenEvents.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.api.ApiCommunicator;

public class UserModel {

    private static final UserModel singleton = new UserModel();
    private static final String TOKEN_KEY = "token";

    private final SharedPreferences spToken;
    private String token;


    public static UserModel getInstance(){
        return singleton;
    }


    private UserModel(){
        Context c = OpenEventsApplication.getAppContext();
        spToken = c.getSharedPreferences(c.getString(R.string.token_preference_file), Context.MODE_PRIVATE);

        token = spToken.getString(TOKEN_KEY, null);
    }

    public boolean userLoggedIn(){
        return token != null; //If there is no token, user is not logged in
    }


    public boolean logIn(String user, String password){
        ApiCommunicator.makeRequest();
        return true;
    }

    public void logOut(){
        deleteToken();
    }

    public String register(String user, String password){
        ApiCommunicator.makeRequest();
        return null;
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
}
