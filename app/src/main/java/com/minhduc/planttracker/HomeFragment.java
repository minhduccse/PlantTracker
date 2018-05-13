package com.minhduc.planttracker;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    String JSON_STRING;

    JSONObject jsonObject;
    JSONArray jsonArray;

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

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateHomeFragment();
                final Runnable runnable = this;
                handler.postDelayed(runnable, 5000);
            }
        }, 5000);

        return view;
    }

    void updateHomeFragment(){
        new JSONTask().execute();
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

    public class  JSONTask extends AsyncTask<Void, Void, String>{
        String JSON_URL;
        @Override
        protected void onPreExecute() {
            JSON_URL = "https://api.thingspeak.com/channels/471086/feeds.json?results=1";
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

            while ((JSON_STRING = bufferedReader.readLine()) != null){
                stringBuilder.append(JSON_STRING + "\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            try {
                jsonObject = new JSONObject(stringBuilder.toString().trim());
                jsonArray = jsonObject.getJSONArray("feeds");

                JSONObject feeds = jsonArray.getJSONObject(0);
                x = feeds.getInt("field1");
                y = feeds.getInt("field2");
                z = feeds.getInt("field3");
            } catch (JSONException e){
                e.printStackTrace();
            }

            return stringBuilder.toString().trim();

            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            itemAdapter.notifyDataSetChanged();
        }
    }
}
