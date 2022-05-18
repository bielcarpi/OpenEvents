package com.grup8.OpenEvents.controller.fragments;


import android.content.Intent;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.CommentActivity;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import org.w3c.dom.Text;

public class DescriptionEventFragment extends Fragment {

    private Event event;

    private TextView txtTitle;
    private ImageView imgEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_description_event, container, false);

        txtTitle = v.findViewById(R.id.name_event);
        imgEvent = v.findViewById(R.id.image_event);

        if(getArguments() == null) return null;
        event = (Event) getArguments().getSerializable("event");

        txtTitle.setText(event.getName());
        ImageHelper.bindImageToEvent(event.getImage(), imgEvent);

        TextView comments = v.findViewById(R.id.event_comments);
        comments.setOnClickListener(view -> startActivity(new Intent(getActivity(), CommentActivity.class)));



        return v;
    }
}