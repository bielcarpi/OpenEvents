package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.model.AssistanceModel;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.entities.Event;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MyEventsFragment extends Fragment {

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<Event> latestEvents;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_events, container, false);
        latestEvents = new ArrayList<>();


        TextView txtFutureEvents = v.findViewById(R.id.myevents_future);
        TextView txtPastEvents = v.findViewById(R.id.myevents_ended);

        txtFutureEvents.setOnClickListener(view -> {
            txtPastEvents.setBackground(null);
            txtFutureEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            showFutureEvents();
        });
        txtPastEvents.setOnClickListener(view -> {
            txtFutureEvents.setBackground(null);
            txtPastEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            showPastEvents();
        });

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.myevents_toolbar);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.my_events);

        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> replaceFragment(v));

        //Set Up Recycle View
        eventRecyclerView = v.findViewById(R.id.user_events_recycleview);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Get all the events so as to see them
        showFutureEvents();

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



    private void showFutureEvents() {
        AssistanceModel.getInstance().getFutureUserAssistances((success, events) -> {
            if(success)
                updateUI(new ArrayList<>(Arrays.asList(events)));
        });
    }

    private void showPastEvents() {
        AssistanceModel.getInstance().getFinishedUserAssistances((success, events) -> {
            if(success)
                updateUI(new ArrayList<>(Arrays.asList(events)));
        });
    }



    private void replaceFragment(View view) {
        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, new HomeFragment()).commit();
    }
}