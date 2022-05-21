package com.grup8.OpenEvents.controller.recyclerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.model.entities.Assistance;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

    private final ArrayList<Assistance> assistances;
    private final Activity activity;

    public CommentAdapter(ArrayList<Assistance> assistances, Activity activity) {
        this.assistances = new ArrayList<>(assistances);
        this.activity = activity;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new CommentHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.bind(assistances.get(position));
    }

    @Override
    public int getItemCount() {
        return assistances.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Assistance> newList){
        assistances.clear();
        assistances.addAll(newList);
        notifyDataSetChanged();
    }
}

