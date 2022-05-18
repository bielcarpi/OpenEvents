package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
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
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchFragment extends Fragment {

    private TextView txtUsers, txtEvents, txtLocation, txtDate;
    private EditText etSearch;
    private RecyclerView searchRecyclerView;
    private EventAdapter eventAdapter;
    private UserAdapter userAdapter;

    private enum SearchType{
        USERS,
        EVENTS,
        LOCATION,
        DATE
    }
    private SearchType currentSearchType;
    private ArrayList<?> latestList;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        currentSearchType = SearchType.USERS;
        latestList = new ArrayList<>();

        //Capture references to views
        etSearch = v.findViewById(R.id.search_bar);
        txtUsers = v.findViewById(R.id.search_users);
        txtEvents = v.findViewById(R.id.search_events);
        txtLocation = v.findViewById(R.id.search_events_location);
        txtDate = v.findViewById(R.id.search_events_date);

        //Set Up text (acting as buttons) Listeners
        txtUsers.setOnClickListener(view -> changeSearchType(SearchType.USERS));
        txtEvents.setOnClickListener(view -> changeSearchType(SearchType.EVENTS));
        txtLocation.setOnClickListener(view -> changeSearchType(SearchType.LOCATION));
        txtDate.setOnClickListener(view -> changeSearchType(SearchType.DATE));

        //Set Up Searchbar Listener
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                switch (currentSearchType){
                    case EVENTS:
                        searchEvents(text);
                        break;
                    case LOCATION:
                        searchEventsByLocation(text);
                        break;
                    case DATE:
                        searchEventsByDate(text);
                        break;
                    default:
                        searchUsers(text);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        searchRecyclerView = v.findViewById(R.id.search_recyclerview);
        searchRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));

        return v;
    }


    @SuppressWarnings("unchecked")
    private void updateUI(SearchType type, ArrayList<?> list) {
        currentSearchType = type;
        latestList = list;

        if (type == SearchType.USERS) {
            if (userAdapter == null) {
                userAdapter = new UserAdapter((ArrayList<User>) list, getActivity());
                searchRecyclerView.setAdapter(userAdapter);
            } else {
                searchRecyclerView.setAdapter(userAdapter);
                userAdapter.updateList((ArrayList<User>) list);
            }
        } else {
            if (eventAdapter == null) {
                eventAdapter = new EventAdapter((ArrayList<Event>) list, getActivity());
                searchRecyclerView.setAdapter(eventAdapter);
            } else {
                searchRecyclerView.setAdapter(eventAdapter);
                eventAdapter.updateList((ArrayList<Event>) list);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(currentSearchType, latestList);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeSearchType(SearchType type){
        currentSearchType = type;
        txtEvents.setBackground(null);
        txtLocation.setBackground(null);
        txtDate.setBackground(null);
        txtUsers.setBackground(null);
        String currentSearch = etSearch.getText().toString();

        switch (type){
            case EVENTS:
                txtEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
                searchEvents(currentSearch);
                break;
            case LOCATION:
                txtLocation.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
                searchEventsByLocation(currentSearch);
                break;
            case DATE:
                txtDate.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
                searchEventsByDate(currentSearch);
                break;
            default:
                txtUsers.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
                searchUsers(currentSearch);
        }
    }

    private void searchUsers(String text) {
        if(text.trim().isEmpty()){
            updateUI(SearchType.USERS, new ArrayList<>());
            return;
        }

        UserModel.getInstance().searchUser(text, (success, users) -> {
            if (success)
                updateUI(SearchType.USERS, new ArrayList<>(Arrays.asList(users)));
        });
    }
    private void searchEvents(String text) {
        if(text.trim().isEmpty()){
            updateUI(SearchType.EVENTS, new ArrayList<>());
            return;
        }

        EventModel.getInstance().searchEventsByKeyword(text, (success, events) -> {
            if (success)
                updateUI(SearchType.EVENTS, new ArrayList<>(Arrays.asList(events)));
        });
    }
    private void searchEventsByLocation(String location) {
        if(location.trim().isEmpty()){
            updateUI(SearchType.LOCATION, new ArrayList<>());
            return;
        }

        EventModel.getInstance().searchEventsByLocation(location, (success, events) -> {
            if (success)
                updateUI(SearchType.LOCATION, new ArrayList<>(Arrays.asList(events)));
        });
    }
    private void searchEventsByDate(String date) {
        if(date.trim().isEmpty()){
            updateUI(SearchType.DATE, new ArrayList<>());
            return;
        }

        EventModel.getInstance().searchEventsByDate(date, (success, events) -> {
            if (success)
                updateUI(SearchType.DATE, new ArrayList<>(Arrays.asList(events)));
        });
    }
}