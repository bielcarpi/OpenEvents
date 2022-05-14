package com.grup8.OpenEvents.controller.fragments;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsHolder> {
    private List<User> lUser;
    private MainActivity activity;

    public FriendsAdapter(List<User> lUser, MainActivity activity) {
        this.lUser = lUser;
        this.activity = activity;
    }

    @Override
    public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new FriendsHolder(layoutInflater, parent, activity);
    }

    @Override
    public void onBindViewHolder(FriendsHolder holder, int position) {
        User user = lUser.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return lUser.size();
    }

}

