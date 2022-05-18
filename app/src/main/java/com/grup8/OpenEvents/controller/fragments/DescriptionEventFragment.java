package com.grup8.OpenEvents.controller.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.CommentActivity;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.text.SimpleDateFormat;


public class DescriptionEventFragment extends Fragment {

    private Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_description_event, container, false);

        TextView txtTitle = v.findViewById(R.id.description_event_title);
        TextView txtStartDate = v.findViewById(R.id.description_event_start_date);
        TextView txtEndDate = v.findViewById(R.id.description_event_end_date);
        TextView txtLocation = v.findViewById(R.id.description_event_location);
        TextView txtDescription = v.findViewById(R.id.description_event_description);
        TextView txtType = v.findViewById(R.id.description_event_type);
        TextView txtSlug = v.findViewById(R.id.description_event_slug);
        TextView txtAssistants = v.findViewById(R.id.description_event_assistants);
        TextView txtMaxAssistants = v.findViewById(R.id.description_event_max_assistants);
        TextView txtComments = v.findViewById(R.id.description_event_comments);
        ImageView imgEvent = v.findViewById(R.id.description_event_image);
        Button btnAssist = v.findViewById(R.id.description_event_assist_btn);

        if(getArguments() == null) return null;
        event = (Event) getArguments().getSerializable("event");

        txtTitle.setText(event.getName());
        txtLocation.setText(event.getLocation());
        txtStartDate.setText(event.getStartDate() == null? "": new SimpleDateFormat("dd-MM-yyyy").format(event.getStartDate().getTime()));
        txtEndDate.setText(event.getEndDate() == null? "": new SimpleDateFormat("dd-MM-yyyy").format(event.getEndDate().getTime()));
        txtDescription.setText(event.getDescription());
        txtType.setText(event.getType().equals("null")? "-": event.getType());
        txtSlug.setText(event.getSlug().equals("null")? "-": event.getSlug());
        txtAssistants.setText("10");
        txtMaxAssistants.setText(Integer.toString(event.getParticipators()));
        txtComments.setOnClickListener(view -> startActivity(new Intent(getActivity(), CommentActivity.class)));
        ImageHelper.bindImageToEvent(event.getImage(), imgEvent);


        return v;
    }
}