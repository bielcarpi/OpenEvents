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
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.entities.UserManager;

import java.util.Arrays;
import java.util.List;

public class FriendsFragment extends Fragment {
    private UserManager userManager = UserManager.getInstance(getActivity());
    private UserAdapter adapter;


    private TextView friends, friends_request;

    private RecyclerView friendsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);


        friendsRecyclerView = (RecyclerView) v.findViewById(R.id.friends_recycleview);
        friendsRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));

        friends = v.findViewById(R.id.friends);
        friends_request = v.findViewById(R.id.friends_request);

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel.getInstance().getCurrentUserFriends( (success, users) -> {
                    System.out.println("Hola! User -> " + success);
                    if(success) {
                        userManager.setlUser(Arrays.asList(users));
                        updateUI();

                    };


                });
            }
        });

        friends_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel.getInstance().getFriendRequests( (success, users) -> {
                    System.out.println("Hola! User -> " + success);
                    if(success) {
                        userManager.setlUser(Arrays.asList(users));
                        updateUI();

                    };


                });
            }
        });


        return v;
    }

    private void updateUI() {

        // demanar info de qui


        List<User> lUser = this.userManager.getlUsers();

            adapter = new UserAdapter(lUser, (MainActivity) getActivity());
            friendsRecyclerView.setAdapter (adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}