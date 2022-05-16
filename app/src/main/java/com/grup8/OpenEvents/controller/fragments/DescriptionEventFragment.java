package com.grup8.OpenEvents.controller.fragments;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.entities.User;
import com.squareup.picasso.Picasso;

public class DescriptionEventFragment extends Fragment {

    private TextView tvName;
    private ImageView ivImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_description_event, container, false);

        tvName = v.findViewById(R.id.name_event);
        ivImage = v.findViewById(R.id.image_event);


        Bundle data = getArguments();
        int id = data.getInt("ID");
        String name = data.getString("NAME");
        String lastName = data.getString("LAST_NAME");
        String email = data.getString("EMAIL");
        String image = data.getString("IMAGE");




        System.out.println(name);

        tvName.setText(name);



        Picasso.get().load(image).into(ivImage);



        return v;
    }
}