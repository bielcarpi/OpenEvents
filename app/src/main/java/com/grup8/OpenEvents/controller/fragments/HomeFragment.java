package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.ChatActivity;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.entities.Event;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<Event> latestEvents;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        latestEvents = new ArrayList<>();

        TextView txtAllEvents = v.findViewById(R.id.home_all_events);
        TextView txtBestEvents = v.findViewById(R.id.home_best_events);
        ImageView imgMessages = v.findViewById(R.id.home_messages);
        ImageView imgFriends = v.findViewById(R.id.home_friends);

        txtAllEvents.setOnClickListener(view -> {
            txtBestEvents.setBackground(null);
            txtAllEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            showAllEvents();
        });
        txtBestEvents.setOnClickListener(view -> {
            txtAllEvents.setBackground(null);
            txtBestEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            showBestEvents();
        });

        imgFriends.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment, new FriendsFragment());
            fragmentTransaction.commit();
        });
        imgMessages.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ChatActivity.class));
        });


        //Set Up Recycle View
        eventRecyclerView = v.findViewById(R.id.home_event_recyclerview);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Get all the events so as to see them
        showAllEvents();

        return v;
    }

    private void updateUI(ArrayList<Event> events) {
        latestEvents = events;

        if(eventAdapter == null){
            eventAdapter = new EventAdapter(events, getActivity());
            eventRecyclerView.setAdapter(eventAdapter);
        }
        else{
            eventAdapter.updateList(events);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(latestEvents);
    }


    private void showBestEvents() {
        EventModel.getInstance().getBestEvents((success, events) -> {
            if(success)
                updateUI(new ArrayList<>(Arrays.asList(events)));
        });
    }
    private void showAllEvents() {
        EventModel.getInstance().getAllEvents((success, events) -> {
            if(success)
                updateUI(new ArrayList<>(Arrays.asList(events)));
        });
    }
}