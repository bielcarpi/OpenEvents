package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.EventManager;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView eventRecyclerView;

    private final EventManager eventManager =  EventManager.getInstance(getActivity());
    private User user;

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

        /*
        bButton = v.findViewById(R.id.message);
        // Tindrem que mirar a quin perfil estem
        bButton.setText("Edit profile");
        bButton = v.findViewById(R.id.settings_btn);
        bButton.setOnClickListener((View.OnClickListener) view -> replaceFragment(view));
         */


        //Setup recycler view
        eventRecyclerView = (RecyclerView) v.findViewById(R.id.home_event_recyclerview);
        eventRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));

        return v;
    }


    private void updateUI() {
        List<Event> lEvents = eventManager.getlEvents();
        EventAdapter adapter = new EventAdapter(lEvents, (MainActivity) getActivity());
        eventRecyclerView.setAdapter (adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void showFutureUserEvents() {
        EventModel.getInstance().getFutureUserEvents(user, (success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
    private void showCurrentUserEvents() {
        EventModel.getInstance().getCurrentUserEvents(user, (success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
    private void showEndedUserEvents() {
        EventModel.getInstance().getFinishedUserEvents(user, (success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
}