package com.grup8.OpenEvents.controller.recyclerview;

import android.app.Activity;
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
import com.squareup.picasso.Picasso;


public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public User user;
    private TextView txtName, txtEmail;
    private ImageView imgUser;
    private Activity activity;

    public UserHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.user_row, parent, false));

        txtName = itemView.findViewById(R.id.user_row_name);
        txtEmail = itemView.findViewById(R.id.user_row_email);
        imgUser = itemView.findViewById(R.id.user_row_image);

        this.activity = activity;
        itemView.setOnClickListener(this);
    }

    public void bind (User user) {
        this.user = user;
        String completeName = user.getName() + " " + user.getLastName();
        txtName.setText(completeName);
        txtEmail.setText(user.getEmail());

        if(user.getImage() != null && !user.getImage().trim().isEmpty()){
            System.out.println("Printing path: " + user.getImage());
            Picasso.get().load(user.getImage()).into(imgUser);
        }
    }

    @Override
    public void onClick(View view) {
        activity = (MainActivity) view.getContext();
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle args = new Bundle();

        args.putSerializable("user", user);
        profileFragment.setArguments(args);

        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, profileFragment).commit();
    }


}
