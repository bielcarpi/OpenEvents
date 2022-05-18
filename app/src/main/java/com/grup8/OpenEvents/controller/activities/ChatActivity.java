package com.grup8.OpenEvents.controller.activities;

import android.os.Bundle;
import android.os.UserManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.*;
import com.grup8.OpenEvents.controller.fragments.FriendsFragment;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.MessageModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {



    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private UserManager userManager;

    private ArrayList<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO aqui falta una crida que retorni tots els usuaris amb els que tenim chat
        // i fer un set al user manager
        list = new ArrayList<>();
        MessageModel.getInstance().getUsersMessaging((success, users) -> {
            if (success)
                updateUI( new ArrayList<>(Arrays.asList(users)));
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        userRecyclerView = findViewById(R.id.chat_recycleview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void updateUI( ArrayList<User> list) {
        this.list = list;
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
        updateUI(list);
    }
}