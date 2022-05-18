package com.grup8.OpenEvents.controller.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.fragments.ProfileFragment;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.util.Arrays;

public class FriendsRequestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public User user;
    private final TextView txtName, txtEmail;
    private final ImageView imgUser;
    private final Button btnAdd, btnDecline;

    public FriendsRequestHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.friends_request_row, parent, false));

        txtName = itemView.findViewById(R.id.user_row_name);
        txtEmail = itemView.findViewById(R.id.user_row_email);
        imgUser = itemView.findViewById(R.id.user_row_image);
        btnAdd = itemView.findViewById(R.id.button_accept);
        btnDecline = itemView.findViewById(R.id.button_decline);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel.getInstance().acceptFriendRequest(user, (success, users) -> {
                    System.out.println("Hola! User -> " + success);


                });
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        itemView.setOnClickListener(this);
    }

    public void bind (User user) {
        this.user = user;
        String completeName = user.getName() + " " + user.getLastName();
        txtName.setText(completeName);
        txtEmail.setText(user.getEmail());

        ImageHelper.bindImageToUser(user.getImage(), imgUser);
    }

    @Override
    public void onClick(View view) {
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle args = new Bundle();
        args.putSerializable("user", user);
        profileFragment.setArguments(args);

        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, profileFragment).commit();
    }

}
