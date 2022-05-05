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
import com.grup8.OpenEvents.model.entities.Event;
import com.grup8.OpenEvents.model.entities.EventManager;

import java.util.List;

public class Fragment1 extends Fragment {

    private RecyclerView eventRecyclerView;
    private EventAdapter adapter;

    private EventManager eventManager = EventManager.getInstance(getActivity());

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_1, container, false);

        // Asignamos los valores al spiner
        String [] values = getResources().getStringArray(R.array.array);
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

        // Recycle View

        eventRecyclerView = (RecyclerView) v.findViewById(R.id.event_recycleview);
        eventRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));


        return v;
    }

    private void updateUI() {
        //Pokedex pokedex = Pokedex.getInstance (getActivity());
        List<Event> lEvents = eventManager.getlEvents();

        if (adapter == null) {
            adapter = new EventAdapter(lEvents, getActivity());
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
}