package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.EventManager;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.entities.UserManager;

import java.util.Arrays;
import java.util.List;


public class SearchFragment extends Fragment {

    private EditText etSearch;


    private UserManager userManager = UserManager.getInstance(getActivity());
    private EventManager eventManager = EventManager.getInstance(getActivity());
    private UserAdapter adapter;
    private EventAdapter eventAdapter;

    private TextView tvUsers, tvEvents;


    private RecyclerView userRecyclerView;

    private int search = 0;
    private String text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = v.findViewById(R.id.search_bar);

        tvUsers = v.findViewById(R.id.users);
        tvEvents = v.findViewById(R.id.events);
        userRecyclerView = (RecyclerView) v.findViewById(R.id.search_recycleview);
        userRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));


        tvUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = 0;
                updateUsers();
            }
        });
        tvEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = 1;
                updateEvents();
            }
        });



        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text = charSequence.toString();
                if (search == 0) {
                    updateUsers();
                } else {
                    updateEvents();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }
    private void updateUI() {

        if(search == 0) {

            List<User> lUser = this.userManager.getlUsers();

            adapter = new UserAdapter(lUser, (MainActivity) getActivity());
            userRecyclerView.setAdapter(adapter);
        } else {
            List<Event> lEvent = this.eventManager.getlEvents();
            eventAdapter = new EventAdapter(lEvent, (MainActivity) getActivity());
            userRecyclerView.setAdapter(eventAdapter);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUsers() {
        UserModel.getInstance().searchUser(text, (success, users) -> {
            System.out.println("Hola! User -> " + success);
            if (success) {
                userManager.setlUser(Arrays.asList(users));
                updateUI();

            }

        });
    }

    public void updateEvents() {
        EventModel.getInstance().searchEventsByKeyword(text, ((success, events) -> {
            if (success) {
                eventManager.setlEvents(Arrays.asList(events));
                updateUI();

            }
        }
        ));

    }





}