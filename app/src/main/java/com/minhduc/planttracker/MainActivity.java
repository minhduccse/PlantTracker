package com.minhduc.planttracker;

import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private HomeFragment homeFragment;
    private TemperatureFragment temperatureFragment;
    private HumidityFragment humidityFragment;
    private MoistureFragment moistureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        homeFragment = new HomeFragment();
        temperatureFragment = new TemperatureFragment();
        humidityFragment = new HumidityFragment();
        moistureFragment = new MoistureFragment();

//        if (savedInstanceState == null){
            setFragment(homeFragment);
            mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_home :
                            setFragment(homeFragment);
                            return true;
                        case R.id.nav_temperature :
                            setFragment(temperatureFragment);
                            return true;
                        case R.id.nav_humidity :
                            setFragment(humidityFragment);
                            return true;
                        case R.id.nav_moisture :
                            setFragment(moistureFragment);
                            return true;
                        default: return false;
                    }
                }
            });
        }
//    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
//
//    public void notificationCall(String content){
//        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .setContentTitle("Plant Tracker")
//                .setContentText(content);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notificationBuilder.build());
//
//    }
}
