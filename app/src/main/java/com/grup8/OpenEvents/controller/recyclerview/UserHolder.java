package com.grup8.OpenEvents.controller.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.fragments.ProfileFragment;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;


public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public User user;
    private final TextView txtName, txtEmail;
    private final ImageView imgUser;

    public UserHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.user_row, parent, false));

        txtName = itemView.findViewById(R.id.user_row_name);
        txtEmail = itemView.findViewById(R.id.user_row_email);
        imgUser = itemView.findViewById(R.id.user_row_image);

        itemView.setOnClickListener(this);
    }

    public void bind (User user) {
        this.user = user;
        String completeName = user.getName() + " " + user.getLastName();
        txtName.setText(completeName);
        txtEmail.setText(user.getEmail());

        ImageHelper.bindImageToUser(user.getImage(), imgUser);
    }

    @Override
    public void onClick(View view) {
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle args = new Bundle();
        args.putSerializable("user", user);
        profileFragment.setArguments(args);

        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, profileFragment).commit();




    }


}
