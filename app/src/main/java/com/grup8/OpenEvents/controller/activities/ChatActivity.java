package com.grup8.OpenEvents.controller.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.*;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.MessageModel;
import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;

    private ArrayList<User> chatsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatsList = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        toolbar.setTitle(R.string.chats);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        MessageModel.getInstance().getUsersMessaging((success, users) -> {
            if (success)
                updateUI(new ArrayList<>(Arrays.asList(users)));
        });

        userRecyclerView = findViewById(R.id.chat_recyclerview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void updateUI( ArrayList<User> list) {
        this.chatsList = list;
        if (userAdapter == null) {
            userAdapter = new UserAdapter(list, this);
            userRecyclerView.setAdapter(userAdapter);
        } else {
            userRecyclerView.setAdapter(userAdapter);
            userAdapter.updateList( list);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI(chatsList);
    }
}