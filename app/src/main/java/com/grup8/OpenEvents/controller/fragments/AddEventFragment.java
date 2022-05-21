package com.grup8.OpenEvents.controller.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.utils.CalendarHelper;
import com.grup8.OpenEvents.model.utils.ImageHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventFragment extends Fragment {


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_event, container, false);

        TextView txtAdd = v.findViewById(R.id.add_event_txt);
        ImageView imgEvent = v.findViewById(R.id.add_event_img);
        EditText etName = v.findViewById(R.id.add_event_name);
        EditText etImage = v.findViewById(R.id.add_event_image);
        EditText etStartDate = v.findViewById(R.id.add_event_start);
        EditText etEndDate = v.findViewById(R.id.add_event_end);
        EditText etLocation = v.findViewById(R.id.add_event_location);
        EditText etParticipants = v.findViewById(R.id.add_event_participants);
        EditText etDescription = v.findViewById(R.id.add_event_description);
        EditText etType = v.findViewById(R.id.add_event_type);
        EditText etSlug = v.findViewById(R.id.add_event_slug);
        TextView txtError = v.findViewById(R.id.add_event_error);
        Button btnPost = v.findViewById(R.id.add_event_btn);

        //Every time the user writes to the image edittext, try to load it
        etImage.setOnFocusChangeListener((view, b) -> ImageHelper.bindImageToEvent(etImage.getText().toString(), imgEvent));


        Calendar startDate = Calendar.getInstance();
        etStartDate.setOnClickListener(view -> {
            final int year = startDate.get(Calendar.YEAR);
            final int month = startDate.get(Calendar.MONTH);
            final int day = startDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (datePicker, y, m, d) -> {
                startDate.set(Calendar.YEAR, y);
                startDate.set(Calendar.MONTH, m);
                startDate.set(Calendar.DAY_OF_MONTH, d);
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                etStartDate.setText(dateFormat.format(startDate.getTime()));
            }, year, month, day);
            datePickerDialog.show();
        });

        Calendar endDate = Calendar.getInstance();
        etEndDate.setOnClickListener(view -> {
            final int year = endDate.get(Calendar.YEAR);
            final int month = endDate.get(Calendar.MONTH);
            final int day = endDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (datePicker, y, m, d) -> {
                endDate.set(Calendar.YEAR, y);
                endDate.set(Calendar.MONTH, m);
                endDate.set(Calendar.DAY_OF_MONTH, d);
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                etEndDate.setText(dateFormat.format(endDate.getTime()));
            }, year, month, day);
            datePickerDialog.show();
        });


        boolean update = false;
        Event updateEvent = null;
        //Do we want to update or not?
        if(getArguments() != null){
            update = getArguments().getBoolean("update", false);
            if(update){
                txtAdd.setText(R.string.update_event);
                updateEvent = (Event) getArguments().getSerializable("event");
                ImageHelper.bindImageToEvent(updateEvent.getImage(), imgEvent);
                etName.setText(updateEvent.getName());
                etImage.setText(updateEvent.getImage());
                etLocation.setText(updateEvent.getLocation());
                etParticipants.setText(Integer.toString(updateEvent.getParticipators()));
                etDescription.setText(updateEvent.getDescription());
                etType.setText(updateEvent.getType());
                etSlug.setText(updateEvent.getSlug());
                btnPost.setText(R.string.update);
            }
        }


        boolean finalUpdate = update;
        Event finalUpdateEvent = updateEvent;
        btnPost.setOnClickListener(view -> {
            btnPost.setEnabled(false);

            String name = etName.getText().toString().trim();
            String imageUrl = etImage.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String maxParticipants = etParticipants.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String type = etType.getText().toString().trim();
            String slug = etSlug.getText().toString().trim();

            if(name.equals("") || imageUrl.equals("") || maxParticipants.equals("") || description.equals("")
                || location.equals("") || type.equals("") || slug.equals("")){
                txtError.setText(R.string.event_error);
                btnPost.setEnabled(true);
                return;
            }

            //If everything is correct, we proceed to create the event
            Event event = new Event(!finalUpdate ? -1: finalUpdateEvent.getId(), name, imageUrl, startDate, endDate, location, Integer.parseInt(maxParticipants),
                    type, description, slug);

            if(finalUpdate){
                EventModel.getInstance().updateEvent(event, (success, errorMessage) -> {
                    if (success){
                        Toast.makeText(this.getContext(), R.string.successful_event_update, Toast.LENGTH_SHORT).show();
                        goToProfile(v);
                    }
                    else{
                        txtError.setText(R.string.internal_app_error);
                        btnPost.setEnabled(true);
                    }
                });
            }
            else{
                EventModel.getInstance().createEvent(event, ((success, errorMessage) -> {
                    if (success){
                        Toast.makeText(this.getContext(), R.string.successful_event_creation, Toast.LENGTH_SHORT).show();
                        goToProfile(v);
                    }
                    else{
                        txtError.setText(R.string.internal_app_error);
                        btnPost.setEnabled(true);
                    }
                }));
            }
        });


        return v;
    }


    public void goToProfile(View view) {
        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, new HomeFragment()).commit();
    }
}

