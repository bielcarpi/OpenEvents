package com.grup8.OpenEvents.controller.recyclerview;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Message;

public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView txtMessage;
    private final LinearLayout parentLayout;

    public MessageHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.message_row, parent, false));
        txtMessage = itemView.findViewById(R.id.message_row_text);
        parentLayout = itemView.findViewById(R.id.message_row_parent);
        itemView.setOnClickListener(this);
    }

    public void bind (Message message) {
        txtMessage.setText(message.getContent());

        //If we sent this message, the gravity will be to the right. If the other user sends it, to the left
        if(message.getSenderId() == UserModel.getInstance().getLoggedInUser().getId())
            parentLayout.setGravity(Gravity.END);
        else
            parentLayout.setGravity(Gravity.START);
    }

    @Override
    public void onClick(View view) {
        //Do nothing on click (for now)
    }
}
