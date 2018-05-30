package com.minhduc.planttracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.CompoundButton;
import android.widget.Button;
import android.widget.ListView;
//import android.widget.Switch;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;

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
    int x, y, z, t;
    String alpha, beta;
    String status, temperature, humidity, moisture, watering;
    String updateStatus, updateTemperature, updateHumidity, updateMoisture, updateWatering;
    String JSON_STRING;

    JSONObject jsonObject;
    JSONArray jsonArray;

    ListView listStatus;
    ArrayList<String> arrItem;

    ArrayAdapter itemAdapter;
    Button start, stop;

    DatabaseReference wateringData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        wateringData = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                t = post.Watering;
                updateHomeFragment();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };

        wateringData.addValueEventListener(postListener);

        start = (Button)  view.findViewById(R.id.start);
        stop = (Button)  view.findViewById(R.id.stop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wateringData.child("Watering").setValue(1);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wateringData.child("Watering").setValue(0);
            }
        });

        listStatus = (ListView) view.findViewById(R.id.listStatus);
        arrItem = new ArrayList<String>();

        status = "Status: ";
        temperature = "Temperature: ";
        humidity = "Humidity: ";
        moisture = "Moisture: ";
        watering = "Watering: ";

        arrItem.add(status);
        arrItem.add(temperature);
        arrItem.add(humidity);
        arrItem.add(moisture);
        arrItem.add(watering);

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
        }, 3000);

        return view;
    }

    void updateHomeFragment(){
        new JSONTask().execute();
        statusCheck();

        updateStatus = status + alpha;
        updateTemperature = temperature + x + "°C";
        updateHumidity = humidity + y + "%";
        updateMoisture = moisture + z + "%";
        updateWatering = watering + beta;

        arrItem.set(0, updateStatus);
        arrItem.set(1, updateTemperature);
        arrItem.set(2, updateHumidity);
        arrItem.set(3, updateMoisture);
        arrItem.set(4, updateWatering);

        itemAdapter.notifyDataSetChanged();
    }

    void statusCheck(){
        if (x < 30){ alpha = "OK"; }
        else if(x >= 30){
            alpha = "Too hot!!!";
        }
        if (t == 0){ beta = "No"; }
        else { beta = "Yes"; }
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
