package com.grup8.OpenEvents.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.recyclerview.CommentAdapter;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.model.entities.Event;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecycleView;

    private CommentAdapter commentAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        EditText etComment = findViewById(R.id.add_comment);
        TextView txtPost = findViewById(R.id.post);
        ImageView imgUser = findViewById(R.id.profile_image);

        commentRecycleView = findViewById(R.id.comments_recycleview);
        commentRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());


        txtPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();
            }
        });

    }




}