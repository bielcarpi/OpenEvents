package com.grup8.OpenEvents.controller.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.entities.Message;

public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Message message;
    private TextView txtMessage;

    public MessageHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.message_row, parent, false));

        txtMessage = itemView.findViewById(R.id.message_row_text);

        itemView.setOnClickListener(this);
    }

    public void bind (Message message) {
        this.message = message;
        txtMessage.setText(message.getContent());
    }

    @Override
    public void onClick(View view) {
    }
}
