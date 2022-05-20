package com.grup8.OpenEvents.controller.recyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.fragments.DescriptionEventFragment;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.text.SimpleDateFormat;

public class EventHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Event event;

    private final TextView txtTitle, txtStartDate, txtEndDate, txtLocation, txtDescription;
    private final ImageView imgEvent;


    public EventHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.event_row, parent, false));

        txtTitle = itemView.findViewById(R.id.event_row_name);
        txtStartDate = itemView.findViewById(R.id.event_row_start_date);
        txtEndDate = itemView.findViewById(R.id.event_row_end_date);
        txtLocation = itemView.findViewById(R.id.event_row_location);
        txtDescription = itemView.findViewById(R.id.event_row_description);
        imgEvent = itemView.findViewById(R.id.event_row_image);

        itemView.setOnClickListener(this);
    }

    @SuppressLint("SimpleDateFormat")
    public void bind (Event event) {
        this.event = event;
        txtTitle.setText(event.getName());
        txtLocation.setText(event.getLocation());
        txtStartDate.setText(event.getStartDate() == null? "": new SimpleDateFormat("dd-MM-yyyy").format(event.getStartDate().getTime()));
        txtEndDate.setText(event.getEndDate() == null? "": new SimpleDateFormat("dd-MM-yyyy").format(event.getEndDate().getTime()));
        txtDescription.setText(event.getDescription());

        ImageHelper.bindImageToEvent(event.getImage(), imgEvent);
    }

    @Override
    public void onClick(View view) {
        DescriptionEventFragment descriptionEventFragment = new DescriptionEventFragment();

        Bundle args = new Bundle();
        args.putSerializable("event", event);
        descriptionEventFragment.setArguments(args);

        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, descriptionEventFragment).commit();
    }
}
