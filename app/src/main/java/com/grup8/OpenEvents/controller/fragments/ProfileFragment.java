package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.EventManager;
import com.grup8.OpenEvents.model.entities.User;

import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView eventRecyclerView;

    private EventManager eventManager =  EventManager.getInstance(getActivity());
    private EventAdapter adapter;

    private ImageView ivImage;
    private TextView tvName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        UserModel.getInstance().updateUserStats((success, user) -> {
            if(success){
                //TODO: Update the stats fields in the view with the user's information
                //user.getNumEvents() ... etc.
            }
        });

        EventModel.getInstance().getBestEvents((success, events) -> {
            if(success){
                //TODO: Depending on the selected option, load a type of user event
                eventManager.setlEvents(Arrays.asList(events));
            }
        });



        eventRecyclerView = (RecyclerView) v.findViewById(R.id.event_recycleview);
        eventRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));




        return v;
    }

    private void updateUI() {
        List<Event> lEvents = eventManager.getlEvents();

        if (adapter == null) {
            adapter = new EventAdapter(lEvents, (MainActivity) getActivity());
            eventRecyclerView.setAdapter (adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


}