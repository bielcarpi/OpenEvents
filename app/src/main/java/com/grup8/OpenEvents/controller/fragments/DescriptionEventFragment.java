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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.AssistantsActivity;
import com.grup8.OpenEvents.controller.activities.CommentActivity;
import com.grup8.OpenEvents.model.AssistanceModel;
import com.grup8.OpenEvents.model.EventModel;
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
    private enum AssistButtonState{
        ASSIST,
        REMOVE_ASSIST
    }
    private AssistButtonState assistButtonState = AssistButtonState.ASSIST;

    private TextView txtTitle, txtScore, txtStartDate, txtEndDate, txtLocation, txtDescription, txtParticipants,
            txtType, txtSlug, txtAssistants, txtMaxAssistants, txtComments, txtUserName, txtUserEmail;
    private ImageView imgUser, imgEvent;
    private LinearLayout userLayout;
    private ImageButton btnBack;
    private Button btnAssist, btnEdit, btnDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n", "UseCompatLoadingForColorStateLists"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_description_event, container, false);

        txtTitle = v.findViewById(R.id.description_event_title);
        txtScore = v.findViewById(R.id.description_event_score);
        txtStartDate = v.findViewById(R.id.description_event_start_date);
        txtEndDate = v.findViewById(R.id.description_event_end_date);
        txtLocation = v.findViewById(R.id.description_event_location);
        txtDescription = v.findViewById(R.id.description_event_description);
        txtType = v.findViewById(R.id.description_event_type);
        txtSlug = v.findViewById(R.id.description_event_slug);
        txtAssistants = v.findViewById(R.id.description_event_assistants);
        txtMaxAssistants = v.findViewById(R.id.description_event_max_assistants);
        txtComments = v.findViewById(R.id.description_event_comments);
        txtParticipants = v.findViewById(R.id.description_event_participants);
        txtUserName = v.findViewById(R.id.description_event_user_name);
        txtUserEmail = v.findViewById(R.id.description_event_user_email);
        imgUser = v.findViewById(R.id.description_event_user_image);
        imgEvent = v.findViewById(R.id.description_event_image);
        userLayout = v.findViewById(R.id.description_event_user_layout);
        btnBack = v.findViewById(R.id.description_event_back);
        btnAssist = v.findViewById(R.id.description_event_assist_btn);
        btnEdit = v.findViewById(R.id.description_event_edit_btn);
        btnDelete = v.findViewById(R.id.description_event_delete_btn);

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


        //Get Assistances and Update the View
        getAssistances();


        //SetUp Assist button functionality
        setUpAssistBtn();


        //If this is an event that the user logged in created, show edit and delete buttons
        if(event.getOwnerId() == UserModel.getInstance().getLoggedInUser().getId()){
            btnEdit.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            //SetUp Assist button functionality
            btnEdit.setOnClickListener(view -> {
                AddEventFragment f = new AddEventFragment();
                Bundle b = new Bundle();
                b.putBoolean("update", true);
                b.putSerializable("event", event);
                f.setArguments(b);
                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, f).commit();
            });

            //SetUp Assist button functionality
            btnDelete.setOnClickListener(view -> {
                btnDelete.setEnabled(false);

                EventModel.getInstance().deleteEvent(event, (success, errorMessage) -> {
                    if (success) {
                        HomeFragment f = new HomeFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, f).commit();
                    }
                    else{
                        btnDelete.setEnabled(false);
                    }
                });
            });
        }


        //SetUp Back Button functionality
        btnBack.setOnClickListener(view -> {
            //TODO: Not working :(
            getParentFragmentManager().popBackStackImmediate();
        });
        return v;
    }


    private void getAssistances(){
        //Request Assistance info and, once we have them, populate the views
        AssistanceModel.getInstance().getAssistancesFromEvent(event, (success, eventAssistances) -> {
            if(success && eventAssistances != null && eventAssistances.length > 0){
                txtAssistants.setText(Integer.toString(eventAssistances.length));
                txtScore.setText(Assistance.getAveragePunctuation(eventAssistances));

                txtParticipants.setOnClickListener(view -> {
                    Intent i = new Intent(getActivity(), AssistantsActivity.class);
                    i.putExtra("assistances", eventAssistances);
                    startActivity(i);
                });

                txtComments.setOnClickListener(view -> {
                    Intent i = new Intent(getActivity(), CommentActivity.class);
                    i.putExtra("assistances", eventAssistances);
                    i.putExtra("event", event);
                    startActivity(i);
                });

                //Check if we're assisting this event
                int currentUserId = UserModel.getInstance().getLoggedInUser().getId();
                boolean assisting = false;
                for (Assistance eventAssistance: eventAssistances) {
                    if (eventAssistance.getAssistant().getId() == currentUserId) {
                        assisting = true;
                        break;
                    }
                }

                btnAssist.setEnabled(true);
                if(assisting){
                    assistButtonState = AssistButtonState.REMOVE_ASSIST;
                    btnAssist.setText(R.string.remove_assistance);
                    btnAssist.setBackgroundTintList(getResources().getColorStateList(R.color.primary_red));
                }
            }
            else{
                btnAssist.setEnabled(true);
                txtAssistants.setText("0");
            }
        });
    }


    private void setUpAssistBtn(){
        btnAssist.setOnClickListener(view -> {
            btnAssist.setEnabled(false);
            if(assistButtonState == AssistButtonState.ASSIST){
                AssistanceModel.getInstance().newAssistance(event, (success, errorMessage) -> {
                    if(success){
                        getAssistances();
                        assistButtonState = AssistButtonState.REMOVE_ASSIST;
                        btnAssist.setText(R.string.remove_assistance);
                        btnAssist.setBackgroundTintList(getResources().getColorStateList(R.color.primary_red));
                        Toast.makeText(this.getContext(), R.string.new_assistance, Toast.LENGTH_SHORT).show();
                    }

                    btnAssist.setEnabled(true);
                });
            }
            else if(assistButtonState == AssistButtonState.REMOVE_ASSIST){
                AssistanceModel.getInstance().removeAssistance(event, (success, errorMessage) -> {
                    if(success){
                        getAssistances();
                        assistButtonState = AssistButtonState.ASSIST;
                        btnAssist.setText(R.string.assist);
                        btnAssist.setBackgroundTintList(getResources().getColorStateList(R.color.primary_blue));
                        Toast.makeText(this.getContext(), R.string.removed_assistance, Toast.LENGTH_SHORT).show();
                    }

                    btnAssist.setEnabled(true);
                });

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getAssistances();
    }
}