package com.minhduc.planttracker;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh(){
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, recent_token);
    }
}

// SAMSUNG J7 Prime:
// eBLL_C4JJIU:APA91bFWArsYbJeiETO44xXQHTB_g__ioqoTzFdiWsGTmF46-Ls-UFIJyjCEgf3nq7PFcR5cgeKKHHYTxEkup6pO7YSZAXfO5Ri9-0vTc29cX4DL7zBYX8UweW7kO7KU7J_oAJzd6-sb
