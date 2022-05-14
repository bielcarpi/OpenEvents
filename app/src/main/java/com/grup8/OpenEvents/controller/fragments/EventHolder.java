package com.grup8.OpenEvents.controller.fragments;

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
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class EventHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Event event;


    private TextView tvNom;
    private TextView tvData;
    private ImageView ivImage;




    private MainActivity activity;

    public EventHolder(LayoutInflater inflater, ViewGroup parent, MainActivity activity) {
        super(inflater.inflate(R.layout.list_adapter, parent, false));

        tvNom = (TextView) itemView.findViewById(R.id.event_name);
        ivImage = itemView.findViewById(R.id.image);


        this.activity = activity;
        itemView.setOnClickListener(this);
    }

    public void bind (Event event) {
        this.event = event;
        tvNom.setText(event.getName());
        Picasso.get().load(event.getImage()).into(ivImage);


    }

    @Override
    public void onClick(View view) {
        // Get the position of the event we clicked

        activity = (MainActivity) view.getContext();
        DescriptionEventFragment descriptionEventFragment = new DescriptionEventFragment();

        // Pass the user clicked
        Bundle args = new Bundle();
        args.putString("NAME", event.getName());
        args.putString("IMAGE", event.getImage());

        // ...


        descriptionEventFragment.setArguments(args);

        FragmentManager fm = activity.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, descriptionEventFragment).commit();


    }


}
