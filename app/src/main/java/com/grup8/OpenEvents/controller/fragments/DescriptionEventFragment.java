package com.grup8.OpenEvents.controller.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.AssistantsActivity;
import com.grup8.OpenEvents.controller.activities.CommentActivity;
import com.grup8.OpenEvents.model.AssistanceModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Assistance;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.User;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;


public class DescriptionEventFragment extends Fragment {

    private Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_description_event, container, false);

        TextView txtTitle = v.findViewById(R.id.description_event_title);
        TextView txtScore = v.findViewById(R.id.description_event_score);
        TextView txtStartDate = v.findViewById(R.id.description_event_start_date);
        TextView txtEndDate = v.findViewById(R.id.description_event_end_date);
        TextView txtLocation = v.findViewById(R.id.description_event_location);
        TextView txtDescription = v.findViewById(R.id.description_event_description);
        TextView txtType = v.findViewById(R.id.description_event_type);
        TextView txtSlug = v.findViewById(R.id.description_event_slug);
        TextView txtAssistants = v.findViewById(R.id.description_event_assistants);
        TextView txtMaxAssistants = v.findViewById(R.id.description_event_max_assistants);
        TextView txtComments = v.findViewById(R.id.description_event_comments);
        TextView txtUserName = v.findViewById(R.id.description_event_user_name);
        TextView txtUserEmail = v.findViewById(R.id.description_event_user_email);
        ImageView imgUser = v.findViewById(R.id.description_event_user_image);
        ImageView imgEvent = v.findViewById(R.id.description_event_image);
        LinearLayout userLayout = v.findViewById(R.id.description_event_user_layout);
        LinearLayout assistancesLayout = v.findViewById(R.id.description_event_assistances_layout);
        ImageButton btnBack = v.findViewById(R.id.description_event_back);
        Button btnAssist = v.findViewById(R.id.description_event_assist_btn);

        if(getArguments() == null) return null;
        event = (Event) getArguments().getSerializable("event");

        txtTitle.setText(event.getName());
        txtLocation.setText(event.getLocation());
        txtStartDate.setText(event.getStartDate() == null? "": new SimpleDateFormat("dd-MM-yyyy").format(event.getStartDate().getTime()));
        txtEndDate.setText(event.getEndDate() == null? "": new SimpleDateFormat("dd-MM-yyyy").format(event.getEndDate().getTime()));
        txtDescription.setText(event.getDescription());
        txtType.setText(event.getType().equals("null")? "-": event.getType());
        txtSlug.setText(event.getSlug().equals("null")? "-": event.getSlug());
        txtMaxAssistants.setText(Integer.toString(event.getParticipators()));
        txtComments.setOnClickListener(view -> startActivity(new Intent(getActivity(), CommentActivity.class)));
        ImageHelper.bindImageToEvent(event.getImage(), imgEvent);

        //Request User info and, once we have them, populate the views
        UserModel.getInstance().getUserById(event.getOwnerId(), (success, users) -> {
            if(success){
                User u = users[0];
                String name = u.getName() + " " + u.getLastName();
                txtUserName.setText(name);
                txtUserEmail.setText(u.getEmail());
                ImageHelper.bindImageToUser(u.getImage(), imgUser);

                userLayout.setOnClickListener(view -> {
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("user", u);
                    profileFragment.setArguments(args);

                    FragmentManager fm = getParentFragmentManager();
                    fm.beginTransaction().replace(R.id.main_fragment, profileFragment).commit();
                });
            }
        });

        //Request Assistance info and, once we have them, populate the views
        AssistanceModel.getInstance().getAssistancesFromEvent(event, (success, eventAssistances) -> {
            if(success && eventAssistances != null && eventAssistances.length > 0){
                txtAssistants.setText(Integer.toString(eventAssistances.length));
                txtScore.setText(Assistance.getAveragePunctuation(eventAssistances));

                assistancesLayout.setOnClickListener(view -> {
                    Intent i = new Intent(getActivity(), AssistantsActivity.class);
                    i.putExtra("assistances", eventAssistances);
                    startActivity(i);
                });

                txtComments.setOnClickListener(view -> {
                    Intent i = new Intent(getActivity(), CommentActivity.class);
                    i.putExtra("assistances", eventAssistances);
                    startActivity(i);
                });
            }
            else{
                txtAssistants.setText("0");
            }
        });



        btnBack.setOnClickListener(view -> {
            //TODO: Not working :(
            getParentFragmentManager().popBackStackImmediate();
        });
        return v;
    }
}