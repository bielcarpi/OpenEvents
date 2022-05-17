package com.grup8.OpenEvents.controller.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.entities.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventHolder> {
    private List<Event> events;
    private MainActivity activity;

    public EventAdapter(List<Event> events, MainActivity activity) {
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

}

