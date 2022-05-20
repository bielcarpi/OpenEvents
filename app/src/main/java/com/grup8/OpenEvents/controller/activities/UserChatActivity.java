package com.grup8.OpenEvents.controller.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.recyclerview.MessageAdapter;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.MessageModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Message;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class UserChatActivity extends AppCompatActivity {

    private RecyclerView messagesRecyclerView;
    private MessageAdapter messageAdapter;
    private User u;

    private ArrayList<Message> messagesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        messagesList = new ArrayList<>();

        u = (User) getIntent().getSerializableExtra("user");
        String userName = u.getName() + " " + u.getLastName();

        Toolbar toolbar = findViewById(R.id.user_chat_toolbar);
        toolbar.setTitle(userName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        TextView txtSend = findViewById(R.id.user_chat_send);
        ImageView imgUser = findViewById(R.id.user_chat_image);
        EditText etWrite = findViewById(R.id.user_chat_write);

        ImageHelper.bindImageToUser(UserModel.getInstance().getLoggedInUser().getImage(), imgUser);
        txtSend.setOnClickListener(view ->
            MessageModel.getInstance().sendMessage(etWrite.getText().toString(), u, (success, errorMessage) -> {
                if (success)
                    updateMessages();
            })
        );

        messagesRecyclerView = findViewById(R.id.user_chat_recyclerview);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Let's update the messages
        updateMessages();

        //Finally, we'll make a new Handler that will take care of fetching messages every 5 seconds
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask asyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> updateMessages());
            }
        };
        timer.schedule(asyncTask, 0, 5000); //Try to fetch new messages every 5 seconds
    }


    private void updateMessages(){
        MessageModel.getInstance().getMessages(u, (success, messages) -> {
            if (success)
                updateUI(new ArrayList<>(Arrays.asList(messages)));
        });
    }


    private void updateUI(ArrayList<Message> list) {
        this.messagesList = list;
        if (messageAdapter == null) {
            messageAdapter = new MessageAdapter(list, this);
            messagesRecyclerView.setAdapter(messageAdapter);
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