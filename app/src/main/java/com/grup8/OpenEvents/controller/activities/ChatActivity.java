package com.grup8.OpenEvents.controller.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.*;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO aqui falta una crida que retorni tots els usuaris amb els que tenim chat
        // i fer un set al user manager

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userRecyclerView = findViewById(R.id.chat_recycleview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void updateUI() {
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}