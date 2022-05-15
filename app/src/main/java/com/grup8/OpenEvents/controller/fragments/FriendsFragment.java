package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsFragment extends Fragment {
    private List<User> lUser;
    private FriendsAdapter adapter;



    private RecyclerView friendsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        lUser = new ArrayList<>();

        friendsRecyclerView = (RecyclerView) v.findViewById(R.id.friends_recycleview);
        friendsRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));


        return v;
    }

    private void updateUI() {

        // demanar info de qui


        List<User> lUser = this.lUser;

        if (adapter == null) {
            adapter = new FriendsAdapter(lUser, (MainActivity) getActivity());
            friendsRecyclerView.setAdapter (adapter);
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