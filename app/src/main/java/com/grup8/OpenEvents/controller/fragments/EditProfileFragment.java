package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;


public class EditProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        EditText etName = v.findViewById(R.id.name_edit_profile);
        EditText etSurname = v.findViewById(R.id.surname_edit_profile);
        EditText etEmail = v.findViewById(R.id.email_edit_profile);
        EditText etPassword  = v.findViewById(R.id.password_edit_profile);

        Button btnUpdate = v.findViewById(R.id.update_btn);
        Button btnCancel = v.findViewById(R.id.cancel_btn);


        btnUpdate.setOnClickListener(view -> {

                User u = new User(
                        etName.getText().toString(),
                        etSurname.getText().toString(),
                        etEmail.getText().toString(),
                        etPassword.getText().toString(), null);

                UserModel.getInstance().updateCurrentUser(u, (success, errorMessage) -> {
                    if (success) {
                        System.out.println(success);
                        replaceFragment(v);
                    } else
                        etEmail.setText(errorMessage);
                });


        });

        btnCancel.setOnClickListener(view -> replaceFragment(v));

        return v;
    }

    public void replaceFragment(View view) {
        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, new ProfileFragment()).commit();
    }}