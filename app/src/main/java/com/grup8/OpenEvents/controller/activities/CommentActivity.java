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
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.utils.ImageHelper;

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
        ImageHelper.bindImageToUser(UserModel.getInstance().getLoggedInUser().getImage(), imgUser);

        commentRecycleView = findViewById(R.id.comments_recyclerview);
        commentRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.comments_toolbar);
        toolbar.setTitle(R.string.comments);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());


        txtPost.setOnClickListener(view -> {
            String comment = etComment.getText().toString();
        });

    }




}