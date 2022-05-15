package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FriendsHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public User user;


    private TextView tvNom;

    private ImageView ivImage;




    private MainActivity activity;

    public FriendsHolder(LayoutInflater inflater, ViewGroup parent, MainActivity activity) {
        super(inflater.inflate(R.layout.friends_adapter, parent, false));

        tvNom = (TextView) itemView.findViewById(R.id.user_name);
        ivImage = itemView.findViewById(R.id.user_image);


        this.activity = activity;
        itemView.setOnClickListener(this);
    }

    public void bind (User user) {
        this.user = user;
        tvNom.setText(user.getName() + " " + user.getLastName());
        Picasso.get().load(user.getImage()).into(ivImage);


    }

    @Override
    public void onClick(View view) {

        activity = (MainActivity) view.getContext();
               // ...

        FragmentManager fm = activity.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, new ProfileFragment()).commit();


    }


}