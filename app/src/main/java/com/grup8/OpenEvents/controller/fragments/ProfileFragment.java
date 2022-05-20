package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.UserChatActivity;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileFragment extends Fragment {

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private User user;
    private ArrayList<Event> latestEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //A Bundle with User must have been provided
        if(getArguments() == null) return null;
        user = (User) getArguments().getSerializable("user");
        latestEvents = new ArrayList<>();

        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //Catch references to the views
        TextView txtName = v.findViewById(R.id.profile_username);
        TextView txtScore = v.findViewById(R.id.profile_score);
        TextView txtNumComments = v.findViewById(R.id.profile_num_comments);
        TextView txtTopPercent = v.findViewById(R.id.profile_top_percent);
        TextView txtNumEvents = v.findViewById(R.id.profile_num_events);
        ImageView imgUser = v.findViewById(R.id.profile_image);
        TextView txtFutureEvents = v.findViewById(R.id.profile_future_events);
        TextView txtCurrentEvents = v.findViewById(R.id.profile_current_events);
        TextView txtEndedEvents = v.findViewById(R.id.profile_ended_events);
        Button btnMessage = v.findViewById(R.id.message);

        //Get the user stats from the server. Once we have them, update the view
        UserModel.getInstance().getUserStats(user, (success, u) -> {
            if(!success) return;

            txtScore.setText(u.getAvgScore() < 0? "-": Float.toString(u.getAvgScore()));
            txtNumComments.setText(Integer.toString(u.getNumComments()));
            String topPercent = u.getPercentageCommentersBelow() < 0? "-": u.getPercentageCommentersBelow() + "%";
            txtTopPercent.setText(topPercent);
            txtNumEvents.setText(Integer.toString(u.getNumEvents()));
        });

        txtFutureEvents.setOnClickListener(view -> {
            txtCurrentEvents.setBackground(null);
            txtEndedEvents.setBackground(null);
            txtFutureEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            showFutureUserEvents();
        });
        txtCurrentEvents.setOnClickListener(view -> {
            txtFutureEvents.setBackground(null);
            txtEndedEvents.setBackground(null);
            txtCurrentEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            showCurrentUserEvents();
        });
        txtEndedEvents.setOnClickListener(view -> {
            txtCurrentEvents.setBackground(null);
            txtFutureEvents.setBackground(null);
            txtEndedEvents.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            showEndedUserEvents();
        });


        String username = user.getName() + " " + user.getLastName();
        txtName.setText(username);
        ImageHelper.bindImageToUser(user.getImage(), imgUser);


        btnMessage.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), UserChatActivity.class);
            i.putExtra("user", user);
            startActivity(i);
        });
        // Tindrem que mirar a quin perfil estem

        //Setup recycler view
        eventRecyclerView = v.findViewById(R.id.home_event_recyclerview);
        eventRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));

        //Get Future User Events so as to see them
        showFutureUserEvents();

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


    private void showFutureUserEvents() {
        EventModel.getInstance().getFutureUserEvents(user, (success, events) -> {
            if (success)
                updateUI(new ArrayList<>(Arrays.asList(events)));
        });
    }
    private void showCurrentUserEvents() {
        EventModel.getInstance().getCurrentUserEvents(user, (success, events) -> {
            if(success)
                updateUI(new ArrayList<>(Arrays.asList(events)));
        });
    }
    private void showEndedUserEvents() {
        EventModel.getInstance().getFinishedUserEvents(user, (success, events) -> {
            if(success)
                updateUI(new ArrayList<>(Arrays.asList(events)));
        });
    }
}