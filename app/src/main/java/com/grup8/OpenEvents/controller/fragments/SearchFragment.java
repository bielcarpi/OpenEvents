package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.entities.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchFragment extends Fragment {

    private EditText etSearch;


    private UserManager userManager = UserManager.getInstance(getActivity());
    private FriendsAdapter adapter;


    private RecyclerView userRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = v.findViewById(R.id.search_bar);


        userRecyclerView = (RecyclerView) v.findViewById(R.id.user_recycleview);
        userRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));





        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                UserModel.getInstance().searchUser(charSequence.toString(), (success, users) -> {
                    System.out.println("Hola! User -> " + success);
                    if(success) {
                        userManager.setlUser(Arrays.asList(users));
                        updateUI();

                    };


                });


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }
    private void updateUI() {

        List<User> lUser = this.userManager.getlUsers();

            adapter = new FriendsAdapter(lUser, (MainActivity) getActivity());
            userRecyclerView.setAdapter (adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }




}