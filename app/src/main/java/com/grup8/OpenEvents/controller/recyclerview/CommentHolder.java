package com.grup8.OpenEvents.controller.recyclerview;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.entities.Assistance;
import com.grup8.OpenEvents.model.utils.ImageHelper;

public class CommentHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final ImageView imgUser;
    private final TextView txtComment;

    public CommentHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.comment_row, parent, false));

        imgUser = itemView.findViewById(R.id.comment_row_image);
        txtComment = itemView.findViewById(R.id.comment_row_text);
        System.out.println("Comment Created!");

        itemView.setOnClickListener(this);
    }

    public void bind(Assistance assistance) {
        ImageHelper.bindImageToUser(assistance.getAssistant().getImage(), imgUser);

        String punctuation = assistance.getPunctuation() == -1? "-": Float.toString(assistance.getPunctuation());
        String comment = txtComment.getContext().getString(R.string.score_emoji) + punctuation +
                " <b>" + assistance.getAssistant().getName() + " " + assistance.getAssistant().getLastName() + "</b> " +
                assistance.getCommentary();

        System.out.println("Setting comment!");
        System.out.println(txtComment);
        txtComment.setText(Html.fromHtml(comment));
    }

    @Override
    public void onClick(View view) {
    }
}
