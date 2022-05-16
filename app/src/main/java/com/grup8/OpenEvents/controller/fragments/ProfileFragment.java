package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.UserModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.EventManager;
import com.grup8.OpenEvents.model.entities.User;

import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView eventRecyclerView;

    private EventManager eventManager =  EventManager.getInstance(getActivity());
    private EventAdapter adapter;

    private MainActivity activity;

    private ImageView ivImage;
    private TextView tvName;
    private ImageButton bButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        User userToView = UserModel.getInstance().getLoggedInUser();
        UserModel.getInstance().getUserStats(userToView, (success, user) -> {
            if(success){
                //TODO: Update the stats fields in the view with the user's information
                //user.getNumEvents() ... etc.
            }
        });



         bButton = v.findViewById(R.id.settings);

        // Tindrem que mirar a quin perfil estem

        //bButton.setText("Edit profile");

        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(view);
            }
        });

        // Asignamos los valores al spiner
        String [] values = getResources().getStringArray(R.array.date);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);



        // Recogemos el valor que ha pulsado del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EventModel.getInstance().getBestEvents((success, events) -> {
            if(success){
                //TODO: Depending on the selected option, load a type of user event
                eventManager.setlEvents(Arrays.asList(events));
            }
        });



        eventRecyclerView = (RecyclerView) v.findViewById(R.id.event_recycleview);
        eventRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));




        return v;
    }

    private void updateUI() {
        List<Event> lEvents = eventManager.getlEvents();

        if (adapter == null) {
            adapter = new EventAdapter(lEvents, (MainActivity) getActivity());
            eventRecyclerView.setAdapter (adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void replaceFragment(View view) {
        activity = (MainActivity) view.getContext();
        // ...

        FragmentManager fm = activity.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, new Fragment2()).commit();
    }


}