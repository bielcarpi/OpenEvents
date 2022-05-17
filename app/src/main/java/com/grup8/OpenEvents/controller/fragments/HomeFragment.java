package com.grup8.OpenEvents.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.controller.activities.MainActivity;
import com.grup8.OpenEvents.controller.recyclerview.EventAdapter;
import com.grup8.OpenEvents.model.EventModel;
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.EventManager;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView eventRecyclerView;
    private final EventManager eventManager = EventManager.getInstance(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Get all the events so as to see them
        showAllEvents();

        //Assign values to the spinner
        String [] values = getResources().getStringArray(R.array.home_events_dropdown);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        //Set Up spinner Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) showAllEvents();
                else showBestEvents();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Set Up Recycle View
        eventRecyclerView = (RecyclerView) v.findViewById(R.id.home_event_recyclerview);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private void updateUI() {
        List<Event> lEvents = eventManager.getlEvents();
        EventAdapter adapter = new EventAdapter(lEvents, (MainActivity) getActivity());
        eventRecyclerView.setAdapter (adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void showBestEvents() {
        EventModel.getInstance().getBestEvents((success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
    private void showAllEvents() {
        EventModel.getInstance().getAllEvents((success, events) -> {
            if(!success) return;
            eventManager.setlEvents(Arrays.asList(events));
            updateUI();
        });
    }
}