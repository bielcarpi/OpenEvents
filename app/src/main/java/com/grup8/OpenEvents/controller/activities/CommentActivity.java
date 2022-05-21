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
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Assistance;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private ArrayList<Assistance> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Assistance[] assistances = (Assistance[]) getIntent().getSerializableExtra("assistances");
        commentsList = new ArrayList<>(Arrays.asList(assistances));
        commentsList.removeIf(a -> a.getCommentary() == null && a.getPunctuation() == -1);

        EditText etComment = findViewById(R.id.add_comment);
        TextView txtPost = findViewById(R.id.post);
        ImageView imgUser = findViewById(R.id.profile_image);
        ImageHelper.bindImageToUser(UserModel.getInstance().getLoggedInUser().getImage(), imgUser);

        commentRecyclerView = findViewById(R.id.comments_recyclerview);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.comments_toolbar);
        toolbar.setTitle(R.string.comments);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());


        txtPost.setOnClickListener(view -> {
            String comment = etComment.getText().toString();
        });

        updateUI(commentsList);
    }


    private void updateUI(ArrayList<Assistance> list) {
        this.commentsList = list;
        if (commentAdapter == null) {
            commentAdapter = new CommentAdapter(list, this);
            commentRecyclerView.setAdapter(commentAdapter);
        } else {
            commentRecyclerView.setAdapter(commentAdapter);
            commentAdapter.updateList(list);
        }

        System.out.println("List -> " + list.size());
        System.out.println(commentAdapter.getItemCount());
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI(commentsList);
    }


}