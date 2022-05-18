package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.recyclerview.CommentAdapter;
import com.grup8.OpenEvents.controller.recyclerview.UserAdapter;
import com.grup8.OpenEvents.model.entities.User;

import java.util.List;


public class CommentFragment extends Fragment {

    private CommentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comment, container, false);

        return v;


    }
    private void updateUI() {

        // demanar info de qui

/*
        List<User> lUser = this.userManager.getlUsers();

        adapter = new CommentAdapter(lUser, (MainActivity) getActivity());
        friendsRecyclerView.setAdapter (adapter);
*/
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}