package com.grup8.OpenEvents.controller.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.entities.Assistance;
import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;

public class AssistantsActivity extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistants);

        Toolbar toolbar = findViewById(R.id.assistants_toolbar);
        toolbar.setTitle(R.string.assistants);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        Assistance[] assistances = (Assistance[]) getIntent().getExtras().getSerializable("assistances");
        ArrayList<User> users = new ArrayList<>();
        for(Assistance a: assistances) users.add(a.getAssistant());

        usersRecyclerView = findViewById(R.id.assistants_recyclerview);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(users, this);
        usersRecyclerView.setAdapter(userAdapter);
    }
}
