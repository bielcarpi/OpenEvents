package com.grup8.OpenEvents.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;


public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText etEmail, etPassword;
    private TextView txtError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.login_btn);
        btnRegister = findViewById(R.id.go_to_register_btn);
        etEmail = findViewById(R.id.email_login);
        etPassword = findViewById(R.id.password_login);
        txtError = findViewById(R.id.login_error);

        btnLogin.setOnClickListener(view -> {
            btnLogin.setEnabled(false);
            UserModel.getInstance().logIn(etEmail.getText().toString(), etPassword.getText().toString(), (success, errorMessage) -> {
                if(success){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    txtError.setText(errorMessage);
                    btnLogin.setEnabled(true);
                }
            });
        });

        btnRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}