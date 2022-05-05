package com.grup8.OpenEvents.controller.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.grup8.OpenEvents.model.UserModel;

@SuppressLint("CustomSplashScreen") //We're targeting Android 7. The Custom SplashScreen is only available from Android 12 onwards
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if the user is logged in. If it is, go to the MainActivity
        if(UserModel.getInstance().userLoggedIn())
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

        //If user is not logged in, go to the Log In activity
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
    }
}