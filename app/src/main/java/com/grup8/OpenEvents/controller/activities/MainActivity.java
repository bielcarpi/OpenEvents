package com.grup8.OpenEvents.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.fragments.Fragment1;
import com.grup8.OpenEvents.controller.fragments.Fragment2;
import com.grup8.OpenEvents.controller.fragments.FriendsFragment;
import com.grup8.OpenEvents.controller.fragments.ProfileFragment;
import com.grup8.OpenEvents.controller.fragments.SearchFragment;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;

public class MainActivity extends AppCompatActivity {

    private ImageButton homeBtn, profileBtn, searchBtn, friendsBtn;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        replaceFragment(new Fragment1());

        homeBtn = findViewById(R.id.fragment1);
        profileBtn = findViewById(R.id.fragemnt2);
        searchBtn = findViewById(R.id.search);
        friendsBtn = findViewById(R.id.friends);



        homeBtn.setOnClickListener(view -> replaceFragment(new Fragment1()));
        searchBtn.setOnClickListener(view -> replaceFragment(new SearchFragment()));
        friendsBtn.setOnClickListener(view -> replaceFragment(new FriendsFragment()));
        profileBtn.setOnClickListener(view -> {
            User user  = UserModel.getInstance().getLoggedInUser();

            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);

            profileFragment.setArguments(bundle);
            replaceFragment(profileFragment);
        });


    }

    private void replaceFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, f);
        fragmentTransaction.commit();
    }
}