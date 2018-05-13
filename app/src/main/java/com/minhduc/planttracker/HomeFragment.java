package com.minhduc.planttracker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    ListView listStatus;
    ArrayList<String> arrItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listStatus = (ListView) view.findViewById(R.id.listStatus);
        arrItem = new ArrayList<String>();

        arrItem.add("Status:");
        arrItem.add("Temperature:");
        arrItem.add("Humidity:");
        arrItem.add("Moisture:");

        ArrayAdapter itemAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrItem);

        listStatus.setAdapter(itemAdapter);

        return view;
    }

}
