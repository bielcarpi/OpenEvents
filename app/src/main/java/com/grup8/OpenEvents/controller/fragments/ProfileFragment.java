package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.LoginActivity;
import com.grup8.OpenEvents.controller.activities.UserChatActivity;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProfileFragment extends Fragment {

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private User user;
    private ArrayList<Event> latestEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //A Bundle with User must have been provided
        if(getArguments() == null) return null;
        user = (User) getArguments().getSerializable("user");
        AtomicBoolean friends = new AtomicBoolean(false);
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
        Button btnAddFriend = v.findViewById(R.id.add_friend);
        Button btnMessage = v.findViewById(R.id.message);
        Button btnEditProfile = v.findViewById(R.id.edit_profile);
        Button btnLogOut = v.findViewById(R.id.log_out);

        //Fill some views
        String username = user.getName() + " " + user.getLastName();
        txtName.setText(username);
        ImageHelper.bindImageToUser(user.getImage(), imgUser);


        //If we're showing our profile, enable the edit profile button
        if(user.getId() == UserModel.getInstance().getLoggedInUser().getId()){
            btnEditProfile.setVisibility(View.VISIBLE);
            btnEditProfile.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                editProfileFragment.setArguments(bundle);
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().replace(R.id.main_fragment, editProfileFragment).commit();
            });
            btnLogOut.setVisibility(View.VISIBLE);
            btnLogOut.setOnClickListener(view -> {
                UserModel.getInstance().logOut();
                startActivity(new Intent(this.getContext(), LoginActivity.class));
            });
        }
        else{
            //If not, enable the send message button and the Friend button
            btnMessage.setVisibility(View.VISIBLE);
            btnMessage.setOnClickListener(view -> {
                Intent i = new Intent(getContext(), UserChatActivity.class);
                i.putExtra("user", user);
                startActivity(i);
            });

            //Retrieve from the server our user friends so as to check if this user is friend or not yet
            UserModel.getInstance().getCurrentUserFriends((success, users) -> {
                if(!success) return;

                friends.set(false);
                for(User u: users){
                    if (u.equals(user)) {
                        friends.set(true);
                        break;
                    }
                }

                if(friends.get()){
                    btnAddFriend.setText(R.string.remove_friend);
                    btnAddFriend.setBackgroundTintList(getResources().getColorStateList(R.color.primary_red));
                }
                else{
                    btnAddFriend.setText(R.string.add_friend);
                    btnAddFriend.setBackgroundTintList(getResources().getColorStateList(R.color.primary_blue));
                }

                btnAddFriend.setVisibility(View.VISIBLE);
            });
            btnAddFriend.setOnClickListener(view -> {
                btnAddFriend.setEnabled(false);
                if(friends.get()){
                    UserModel.getInstance().deleteFriend(user, (success, errorMessage) -> {
                        Toast.makeText(getContext(), R.string.no_longer_friends, Toast.LENGTH_SHORT).show();
                        friends.set(false);
                        btnAddFriend.setText(R.string.add_friend);
                        btnAddFriend.setBackgroundTintList(getResources().getColorStateList(R.color.primary_blue));
                        btnAddFriend.setEnabled(true);
                    });
                }
                else{
                    UserModel.getInstance().makeFriendRequest(user, ((success, errorMessage) -> {
                        Toast.makeText(getContext(), R.string.friend_request_sent, Toast.LENGTH_SHORT).show();
                        btnAddFriend.setEnabled(true);
                    }));
                }

                btnAddFriend.setEnabled(true);
            });
        }

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