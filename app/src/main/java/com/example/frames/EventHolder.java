package com.example.frames;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class EventHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Event event;


    private TextView tvNom;
    private TextView tvData;


    private Activity activity;

    public EventHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.list_adapter, parent, false));

        tvNom = (TextView) itemView.findViewById(R.id.event_name);


        this.activity = activity;
        itemView.setOnClickListener(this);
    }

    public void bind (Event event) {
        this.event = event;
        tvNom.setText(event.getName());

    }

    @Override
    public void onClick(View view) {

    }
}
