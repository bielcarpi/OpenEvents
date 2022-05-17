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
    private User u;

    private TextView txtName, txtScore, txtNumComments, txtTopPercent, txtNumFriends, txtNumEvents;
    private Button btnSettings, btnSendMessage, btnAddRemoveFriend;
    private ImageButton bButton;
    private ImageView imgUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //A Bundle with User must have been provided
        if(getArguments() == null) return null;
        u = (User) getArguments().getSerializable("user");

        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //Get the user stats from the server. Once we have them, update the view
        UserModel.getInstance().getUserStats(u, (success, user) -> {
            if(!success) return;

            txtScore.setText(Float.toString(u.getAvgScore()));
            txtNumComments.setText(Integer.toString(u.getNumComments()));
            String topPercent = u.getPercentageCommentersBelow() + "%";
            txtTopPercent.setText(topPercent);
            txtNumFriends.setText(Integer.toString(u.getNumFriends() == -1? '-': u.getNumFriends()));
            txtNumEvents.setText(Integer.toString(u.getNumEvents()));
        });

        //Catch references to the views
        txtName = v.findViewById(R.id.profile_username);
        txtScore = v.findViewById(R.id.profile_score);
        txtNumComments = v.findViewById(R.id.profile_num_comments);
        txtTopPercent = v.findViewById(R.id.profile_top_percent);
        txtNumFriends = v.findViewById(R.id.profile_num_friends);
        txtNumEvents = v.findViewById(R.id.profile_num_events);
        imgUser = v.findViewById(R.id.profile_image);

        txtName.setText(u.getName());
        ImageHelper.bindImageToUser(u.getImage(), imgUser);

        /*
        bButton = v.findViewById(R.id.message);
        // Tindrem que mirar a quin perfil estem
        bButton.setText("Edit profile");
        bButton = v.findViewById(R.id.settings_btn);
        bButton.setOnClickListener((View.OnClickListener) view -> replaceFragment(view));
         */

        /*
        //Assign Values to the spinner
        String [] values = getResources().getStringArray(R.array.user_events_dropdown);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);


        //Setup Spinner Listenner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) showFutureUserEvents();
                else if(i == 1) showCurrentUserEvents();
                else showFinishedUserEvents();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


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
        EventModel.getInstance().getFutureUserEvents(u, (success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
    private void showCurrentUserEvents() {
        EventModel.getInstance().getCurrentUserEvents(u, (success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
    private void showFinishedUserEvents() {
        EventModel.getInstance().getFinishedUserEvents(u, (success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
}