package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;

import java.util.Arrays;


public class SearchFragment extends Fragment {

    private EditText etSearch;
    private ImageButton bSearch;

    private MainActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = v.findViewById(R.id.search_bar);
        bSearch = v.findViewById(R.id.search_button);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = etSearch.getText().toString();

                UserModel.getInstance().searchUser(search, (success, user) -> {
                    System.out.println("Hola! User -> " + success);
                    if(success);
                    System.out.println(user[0].getName());;

                    replaceFragment(v);
                });

            }
        });

        return v;
    }

    public void replaceFragment(View view) {
        activity = (MainActivity) view.getContext();
        // ...

        FragmentManager fm = activity.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.search_frame, new UserSearchFragment()).commit();
    }


}