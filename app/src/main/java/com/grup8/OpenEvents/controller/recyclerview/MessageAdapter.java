package com.grup8.OpenEvents.controller.recyclerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.grup8.OpenEvents.model.entities.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {
    private ArrayList<Message> messages;
    private final Activity activity;

    public MessageAdapter(ArrayList<Message> messages, Activity activity) {
        this.messages = messages;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new MessageHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Message> list) {
        messages.clear();
        messages.addAll(list);
        notifyDataSetChanged();
    }
}

