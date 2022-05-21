package com.grup8.OpenEvents.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.recyclerview.CommentAdapter;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.AssistanceModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Assistance;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private ArrayList<Assistance> commentsList;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Assistance[] assistances = (Assistance[]) getIntent().getSerializableExtra("assistances");
        Event event = (Event) getIntent().getSerializableExtra("event");
        if(assistances != null){
            commentsList = new ArrayList<>(Arrays.asList(assistances));
            commentsList.removeIf(a -> a.getCommentary() == null && a.getPunctuation() == -1);
        }
        else{
            commentsList = new ArrayList<>();
        }

        EditText etComment = findViewById(R.id.comment_write_comment);
        EditText etScore = findViewById(R.id.comment_write_score);
        TextView txtPost = findViewById(R.id.comment_post);
        ImageView imgUser = findViewById(R.id.comment_image);
        ImageHelper.bindImageToUser(UserModel.getInstance().getLoggedInUser().getImage(), imgUser);

        commentRecyclerView = findViewById(R.id.comments_recyclerview);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.comments_toolbar);
        toolbar.setTitle(R.string.comments);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());


        //Check if the user is a participant too, and thus, it can comment
        boolean userIsAssistant = false;
        if(assistances != null){
            for(Assistance a: assistances){
                if (a.getAssistant().getId() == UserModel.getInstance().getLoggedInUser().getId()) {
                    userIsAssistant = true;
                    break;
                }
            }
        }
        if(userIsAssistant){
            txtPost.setTextColor(getResources().getColor(R.color.primary_blue));
            txtPost.setOnClickListener(view -> {
                txtPost.setEnabled(false);
                txtPost.setTextColor(getResources().getColor(R.color.primary_gray));
                String comment = etComment.getText().toString();
                String score = etScore.getText().toString();

                //If both are empty, do nothing
                if(comment.trim().equals("") && score.trim().equals("")){
                    txtPost.setEnabled(true);
                    Toast.makeText(getApplicationContext(), R.string.incorrect_email_format,  Toast.LENGTH_SHORT).show();
                    txtPost.setTextColor(getResources().getColor(R.color.primary_blue));
                    return;
                }

                //If a score has been introduced, check if it's correct
                if(!etScore.getText().toString().trim().equals("")){
                    int scoreInt = Integer.parseInt(score);
                    if(scoreInt < 0 || scoreInt > 10){
                        txtPost.setEnabled(true);
                        Toast.makeText(getApplicationContext(), R.string.incorrect_email_format,  Toast.LENGTH_SHORT).show();
                        txtPost.setTextColor(getResources().getColor(R.color.primary_blue));
                        return;
                    }
                }

                //If everything is correct, post the comment
                AssistanceModel.getInstance().updateAssistance(event, score.trim().equals("")? -1: Integer.parseInt(score),
                        comment, (success, errorMessage) -> {
                    if(success){
                        commentsList.add(new Assistance(event.getId(), UserModel.getInstance().getLoggedInUser(),
                                Float.parseFloat(score), comment));
                        etScore.setText("");
                        etComment.setText("");
                        updateUI(commentsList);
                    }

                    txtPost.setEnabled(true);
                    txtPost.setTextColor(getResources().getColor(R.color.primary_blue));
                });
            });
        }

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
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI(commentsList);
    }


}