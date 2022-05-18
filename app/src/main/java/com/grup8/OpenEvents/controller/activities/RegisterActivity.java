package com.grup8.OpenEvents.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;

public class RegisterActivity extends AppCompatActivity {


    private EditText etName, etSurname, etEmail, etPassword, etConfirmPassword;

    private TextView txtError, txtLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.name_register);
        etSurname = findViewById(R.id.surname_register);
        etEmail = findViewById(R.id.email_register);
        etPassword = findViewById(R.id.password_register);
        etConfirmPassword = findViewById(R.id.confirm_password_register);
        txtError = findViewById(R.id.register_error);

        btnRegister = findViewById(R.id.register_btn);
        txtLogin = findViewById(R.id.go_to_login_btn);


        btnRegister.setOnClickListener(view -> {
            btnRegister.setEnabled(false);

            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();
            if (password.equals(confirmPassword)){
                User newUser = new User(etName.getText().toString(), etSurname.getText().toString(), etEmail.getText().toString(), password, "http://google.com");
                UserModel.getInstance().register(newUser, (success, errorMessage) -> {
                    if(success){
                        Toast.makeText(RegisterActivity.this, R.string.successful_register, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                    else{
                        txtError.setText(errorMessage);
                        btnRegister.setEnabled(true);
                    }
                });
            }
            else{
                txtError.setText(R.string.passwords_dont_match);
                btnRegister.setEnabled(true);
            }

        });

        txtLogin.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }
}