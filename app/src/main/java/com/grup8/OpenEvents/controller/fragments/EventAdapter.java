package com.grup8.OpenEvents.controller.fragments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.model.entities.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventHolder> {
    private List<Event> lEvent;
    private Activity activity;

    public EventAdapter(List<Event> lEvents, Activity activity) {
        this.lEvent = lEvents;
        this.activity = activity;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new EventHolder(layoutInflater, parent, activity);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        Event event = lEvent.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return lEvent.size();
    }

}

