package com.grup8.OpenEvents.controller.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.entities.Message;

public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Message message;

    public MessageHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.event_row, parent, false));
        itemView.setOnClickListener(this);
    }

    public void bind (Message message) {
        this.message = message;
    }

    @Override
    public void onClick(View view) {
    }
}
