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

public class CommentHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Event event;



    public CommentHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.event_row, parent, false));



        itemView.setOnClickListener(this);
    }

    public void bind (Event event) {
        this.event = event;

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
