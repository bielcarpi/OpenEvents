package com.grup8.OpenEvents.controller.fragments;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.grup8.OpenEvents.R;

public class DescriptionEventFragment extends Fragment {

    private TextView tvName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_description_event, container, false);

        Bundle data = getArguments();
        String name = data.getString("NAME");
        String image = data.getString("IMAGE");
        System.out.println(name);

        tvName.setText(name);

        return v;
    }
}