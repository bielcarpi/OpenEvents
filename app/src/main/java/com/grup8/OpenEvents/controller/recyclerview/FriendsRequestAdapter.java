package com.grup8.OpenEvents.controller.recyclerview;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.model.entities.User;

import java.util.List;

public class FriendsRequestAdapter extends RecyclerView.Adapter<FriendsRequestHolder> {
    private final List<User> users;
    private final Activity activity;

    public FriendsRequestAdapter(List<User> users, Activity activity) {
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FriendsRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflates the layout from XML when needed
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new FriendsRequestHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(FriendsRequestHolder holder, int position) {
        //Binds the data to the XML for each row needed
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}

