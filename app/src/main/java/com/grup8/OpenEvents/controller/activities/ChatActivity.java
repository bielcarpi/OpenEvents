package com.grup8.OpenEvents.controller.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.*;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.entities.UserManager;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private UserManager userManager = UserManager.getInstance(this);
    private UserAdapter adapter;

    private RecyclerView userRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO aqui falta una crida que retorni tots els usuaris amb els que tenim chat
        // i fer un set al user manager

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userRecyclerView = (RecyclerView) findViewById(R.id.chat_recycleview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    private void updateUI() {



            List<User> lUser = this.userManager.getlUsers();

            adapter = new UserAdapter(lUser, (ChatActivity) this);
            userRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}