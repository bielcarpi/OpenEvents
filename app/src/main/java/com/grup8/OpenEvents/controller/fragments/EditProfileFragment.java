package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;


public class EditProfileFragment extends Fragment {

    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments() == null) return null;
        user = (User) getArguments().getSerializable("user");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ImageView imgUser = v.findViewById(R.id.profile_image);
        EditText etName = v.findViewById(R.id.name_edit_profile);
        EditText etSurname = v.findViewById(R.id.surname_edit_profile);
        EditText etImage = v.findViewById(R.id.image_edit_profile);
        EditText etEmail = v.findViewById(R.id.email_edit_profile);
        EditText etPassword  = v.findViewById(R.id.password_edit_profile);

        Button btnUpdate = v.findViewById(R.id.update_btn);
        Button btnCancel = v.findViewById(R.id.cancel_btn);
        TextView txtError = v.findViewById(R.id.update_profile_error);
        ImageHelper.bindImageToUser(user.getImage(), imgUser);

        //Every time the user writes to the image edittext, try to load it
        etImage.setOnFocusChangeListener((view, b) -> ImageHelper.bindImageToUser(etImage.getText().toString(), imgUser));

        etName.setText(user.getName());
        etSurname.setText(user.getLastName());
        etImage.setText(user.getImage());
        etEmail.setText(user.getEmail());

        btnUpdate.setOnClickListener(view -> {
            User u = new User(
                    etName.getText().toString(),
                    etSurname.getText().toString(),
                    etEmail.getText().toString(),
                    etPassword.getText().toString(),
                    etImage.getText().toString());

            UserModel.getInstance().updateCurrentUser(u, (success, errorMessage) -> {
                if (success) {
                    Toast.makeText(getContext(), R.string.successful_profile_update, Toast.LENGTH_SHORT).show();
                    replaceFragment(v);
                }
                else
                    txtError.setText(errorMessage);
            });
        });

        btnCancel.setOnClickListener(view -> replaceFragment(v));
        return v;
    }


    public void replaceFragment(View view) {
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        profileFragment.setArguments(bundle);

        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, profileFragment).commit();
    }}