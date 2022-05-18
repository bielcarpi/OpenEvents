package com.grup8.OpenEvents.controller.recyclerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.model.entities.Event;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventHolder> {
    private ArrayList<Event> events;
    private Activity activity;

    public EventAdapter(ArrayList<Event> events, Activity activity) {
        this.events = events;
        this.activity = activity;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new EventHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Event> newList){
        events.clear();
        events.addAll(newList);
        notifyDataSetChanged();
    }

}

