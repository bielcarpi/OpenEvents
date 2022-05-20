package com.grup8.OpenEvents.controller.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.recyclerview.MessageAdapter;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.MessageModel;
import com.grup8.OpenEvents.model.entities.Message;
import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class UserChatActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private MessageAdapter messageAdapter;

    private ArrayList<Message> messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messagesList = new ArrayList<>();

        User u = (User) getIntent().getSerializableExtra("user");
        String userName = u.getName() + u.getLastName();

        Toolbar toolbar = findViewById(R.id.user_chat_toolbar);
        toolbar.setTitle(userName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        MessageModel.getInstance().getMessages(u, (success, messages) -> {
            if (success)
                updateUI(new ArrayList<>(Arrays.asList(messages)));
        });

        userRecyclerView = findViewById(R.id.chat_recyclerview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void updateUI(ArrayList<Message> list) {
        this.messagesList = list;
        if (messageAdapter == null) {
            messageAdapter = new MessageAdapter(list, this);
            userRecyclerView.setAdapter(messageAdapter);
        } else {
            messageAdapter.updateList(list);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI(messagesList);
    }
}