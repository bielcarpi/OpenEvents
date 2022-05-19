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

public class UpdatePostFragment extends Fragment {

    private String startDate, endDate;

    private Event event;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =  inflater.inflate(R.layout.fragment_add_post, container, false);

        // TODO Quan li donis al boto de update pasa el event, mersi

        Bundle args = getArguments();
        event = (Event) args.getSerializable("event");

        EditText etName = v.findViewById(R.id.add_event_name);
        EditText etStartDate = v.findViewById(R.id.add_event_start);
        EditText etEndDate = v.findViewById(R.id.add_event_end);
        EditText etLocation = v.findViewById(R.id.add_event_location);
        EditText etParticipants = v.findViewById(R.id.add_event_participants);
        EditText etType = v.findViewById(R.id.add_event_type);
        EditText etDescription = v.findViewById(R.id.add_event_description);

        Button btnUpdate = v.findViewById(R.id.post_btn);
        btnUpdate.setText(R.string.update);
        Button btnCancel = v.findViewById(R.id.cancel_btn);

        // TODO he pensat que estaria be mostrar les dades que hi han abans d'actualitzar
        //  a cada camp
        etName.setText(event.getName());
        etStartDate.setText(event.getStartDate().getTime().toString());
        etEndDate.setText(event.getEndDate().getTime().toString());
        etLocation.setText(event.getLocation());
        etParticipants.setText(event.getParticipators());
        etType.setText(event.getType());
        etDescription.setText(event.getDescription());




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





        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    // TODO: Aqui la data es guarda be en format Calendar el problema esta
                    //  a l'hora de pasar el event en el EventModel la funcio de create on
                    //  fas el getStartDate i getEndDate has de pasar-ho a
                    //  format YYYY-MM-DDThh:mm:ss.000Z




                    Event e = new Event(event.getId(),
                            etName.getText().toString(),
                            CalendarHelper.getCalendar(startDate),
                            CalendarHelper.getCalendar(endDate),
                            etLocation.getText().toString(),
                            Integer.parseInt(etParticipants.getText().toString()),
                            etType.getText().toString(),
                            etDescription.getText().toString());

                    EventModel.getInstance().updateEvent(e, ((success, errorMessage) -> {
                        if(success) {
                            System.out.println(success);
                            replaceFragment(v);
                        }
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


