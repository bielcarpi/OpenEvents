package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.recyclerview.FriendRequestAdapter;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsFragment extends Fragment {

    private RecyclerView friendsRecyclerView;
    private UserAdapter friendsAdapter;
    private FriendRequestAdapter friendRequestAdapter;

    private enum FriendsType{
        FRIEND,
        FRIEND_REQUEST
    }
    private FriendsType currentFriendsType;
    private ArrayList<?> latestList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        latestList = new ArrayList<>();

        friendsRecyclerView = v.findViewById(R.id.friends_recyclerview);
        friendsRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));

        TextView txtFriends = v.findViewById(R.id.friends);
        TextView txtFriendsRequest = v.findViewById(R.id.friends_request);

        txtFriends.setOnClickListener(view -> {
            txtFriendsRequest.setBackground(null);
            txtFriends.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            searchFriends();
        });
        txtFriendsRequest.setOnClickListener(view -> {
            txtFriends.setBackground(null);
            txtFriendsRequest.setBackground(requireContext().getDrawable(R.drawable.bg_bottom_selected));
            searchFriendRequests();
        });

        //Lastly, search for friends and display them
        searchFriends();
        return v;
    }


    @SuppressWarnings("unchecked")
    private void updateUI(FriendsType friendsType, ArrayList<?> list) {
        currentFriendsType = friendsType;
        latestList = list;

        if (friendsType == FriendsType.FRIEND) {
            if (friendsAdapter == null) {
                friendsAdapter = new UserAdapter((ArrayList<User>) list, getActivity());
                friendsRecyclerView.setAdapter(friendsAdapter);
            } else {
                friendsRecyclerView.setAdapter(friendsAdapter);
                friendsAdapter.updateList((ArrayList<User>) list);
            }
        } else {
            if (friendRequestAdapter == null) {
                friendRequestAdapter = new FriendRequestAdapter((ArrayList<User>) list, getActivity());
                friendsRecyclerView.setAdapter(friendRequestAdapter);
            } else {
                friendsRecyclerView.setAdapter(friendRequestAdapter);
                friendRequestAdapter.updateList((ArrayList<User>) list);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(currentFriendsType, latestList);
    }


    private void searchFriends(){
        UserModel.getInstance().getCurrentUserFriends((success, users) -> {
            if (success)
                updateUI(FriendsType.FRIEND, new ArrayList<>(Arrays.asList(users)));
        });
    }
    private void searchFriendRequests(){
        UserModel.getInstance().getFriendRequests((success, users) -> {
            if (success)
                updateUI(FriendsType.FRIEND_REQUEST, new ArrayList<>(Arrays.asList(users)));
        });
    }
}