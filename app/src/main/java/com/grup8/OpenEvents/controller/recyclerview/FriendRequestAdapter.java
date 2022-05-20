package com.grup8.OpenEvents.controller.recyclerview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestHolder> {
    private final ArrayList<User> users;
    private final Activity activity;

    public FriendRequestAdapter(ArrayList<User> users, Activity activity) {
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FriendRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflates the layout from XML when needed
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new FriendRequestHolder(layoutInflater, parent, this);
    }

    @Override
    public void onBindViewHolder(FriendRequestHolder holder, int position) {
        //Binds the data to the XML for each row needed
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<User> newList){
        users.clear();
        users.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeRequest(int position){
        users.remove(position);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, users.size());
    }
}

