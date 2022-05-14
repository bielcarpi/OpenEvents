package com.grup8.OpenEvents.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.fragments.Fragment1;
import com.grup8.OpenEvents.controller.fragments.Fragment2;
import com.grup8.OpenEvents.controller.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private Button homeBtn, profileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeBtn = (Button) findViewById(R.id.fragment1);
        profileBtn = (Button) findViewById(R.id.fragemnt2);
        replaceFragment(new Fragment1());

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new Fragment1());
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ProfileFragment());
            }
        });

    }

    private void replaceFragment(Fragment f) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();
    }
}