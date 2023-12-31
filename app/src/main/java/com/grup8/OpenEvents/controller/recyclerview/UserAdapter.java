package com.grup8.OpenEvents.controller.recyclerview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {
    private final ArrayList<User> users;
    private final Activity activity;

    public UserAdapter(ArrayList<User> users, Activity activity) {
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflates the layout from XML when needed
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new UserHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
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
}

