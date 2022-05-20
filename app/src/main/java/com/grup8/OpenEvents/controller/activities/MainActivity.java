package com.grup8.OpenEvents.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageButton;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.fragments.AddPostFragment;
import com.grup8.OpenEvents.controller.fragments.HomeFragment;
import com.grup8.OpenEvents.controller.fragments.MyAssistancesFragment;
import com.grup8.OpenEvents.controller.fragments.ProfileFragment;
import com.grup8.OpenEvents.controller.fragments.SearchFragment;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;

public class MainActivity extends AppCompatActivity {

    private ImageButton homeBtn, profileBtn, searchBtn, friendsBtn, addBtn;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        replaceFragment(new HomeFragment());

        homeBtn = findViewById(R.id.fragment1);
        profileBtn = findViewById(R.id.fragemnt2);
        searchBtn = findViewById(R.id.search);
        friendsBtn = findViewById(R.id.friends);
        addBtn = findViewById(R.id.add_event);


        homeBtn.setOnClickListener(view -> replaceFragment(new HomeFragment()));
        searchBtn.setOnClickListener(view -> replaceFragment(new SearchFragment()));
        friendsBtn.setOnClickListener(view -> replaceFragment(new MyAssistancesFragment()));
        addBtn.setOnClickListener(view -> replaceFragment(new AddPostFragment()));
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

    @Override
    public void onBackPressed() {
        //TODO: Not working :( -> We would need to reuse the fragments, so as that its reference is not being lost
        fm.popBackStackImmediate();
    }
}