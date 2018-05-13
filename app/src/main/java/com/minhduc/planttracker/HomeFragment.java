package com.minhduc.planttracker;


import android.os.Bundle;
import android.os.Handler;
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
    int x, y, z;
    String alpha;
    String status, temperature, humidity, moisture;
    String updateStatus, updateTemperature, updateHumidity, updateMoisture;

    ListView listStatus;
    ArrayList<String> arrItem;

    ArrayAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listStatus = (ListView) view.findViewById(R.id.listStatus);
        arrItem = new ArrayList<String>();

        status = "Status: ";
        temperature = "Temperature: ";
        humidity = "Humidity: ";
        moisture = "Moisture: ";

        arrItem.add(status);
        arrItem.add(temperature);
        arrItem.add(humidity);
        arrItem.add(moisture);

        itemAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrItem);

        listStatus.setAdapter(itemAdapter);

        updateHomeFragment();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateHomeFragment();
            }
        }, 5000);

        return view;
    }

    void updateHomeFragment(){
        methodGET();
        statusCheck();

        updateStatus = status + alpha;
        updateTemperature = temperature + x + "°C";
        updateHumidity = humidity + y + "%";
        updateMoisture = moisture + z + "%";

        arrItem.set(0, updateStatus);
        arrItem.set(1, updateTemperature);
        arrItem.set(2, updateHumidity);
        arrItem.set(3, updateMoisture);

        itemAdapter.notifyDataSetChanged();
    }

    void statusCheck(){
        if (x < 30){ alpha = "OK"; }
        else if(x >= 30){ alpha = "Too hot!!!";}
    }

    void methodGET(){
        x = 29; y = 90; z = 90;
    }
}
