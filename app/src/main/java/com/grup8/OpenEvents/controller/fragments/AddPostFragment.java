package com.grup8.OpenEvents.controller.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.utils.CalendarHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class AddPostFragment extends Fragment {

    DatePickerDialog.OnDateSetListener setListener;
    private String startDate, endDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_post, container, false);

        EditText etName = v.findViewById(R.id.add_event_name);
        EditText etStartDate = v.findViewById(R.id.add_event_start);
        EditText etEndDate = v.findViewById(R.id.add_event_end);
        EditText etLocation = v.findViewById(R.id.add_event_location);
        EditText etParticipants = v.findViewById(R.id.add_event_participants);
        EditText etType = v.findViewById(R.id.add_event_type);
        EditText etDescription = v.findViewById(R.id.add_event_description);

        Button btnPost = v.findViewById(R.id.post_btn);
        Button btnCancel = v.findViewById(R.id.cancel_btn);



        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);




        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startDate = i + "-" + i1 + "-" + i2 +"T00:00:00.000Z";
                        System.out.println(startDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        endDate = i + "-" + i1 + "-" + i2 +"T00:00:00.000Z";
                        System.out.println(endDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });





        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Event event = new Event(etName.getText().toString(),
                            CalendarHelper.getCalendar(startDate),
                            CalendarHelper.getCalendar(endDate),
                            etLocation.getText().toString(),
                            Integer.parseInt(etParticipants.getText().toString()),
                            etType.getText().toString(),
                            etDescription.getText().toString());

                    EventModel.getInstance().createEvent(event, ((success, errorMessage) -> {
                        System.out.println(success);
                    }));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(view);
            }
        });

        return v;
    }

    public void replaceFragment(View view) {
        FragmentManager fm = ((MainActivity)view.getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment, new HomeFragment()).commit();
    }



    }

