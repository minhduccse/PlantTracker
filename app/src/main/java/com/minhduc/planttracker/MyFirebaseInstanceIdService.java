package com.minhduc.planttracker;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh(){
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("plant");
        Log.d(REG_TOKEN, recent_token);
    }
}

// SAMSUNG J7 Prime:
// dH_sRKCEVug:APA91bHcd3FblZpdJ516xpN-_nP2Wl8an3d3z0IrKIDbYw4XdzEx9BeZ1aUmnx95H86T6iMET61gLODhhQjApA8SdIHk-FBUGocFH9UJg6Qa88SU4CpZQxx1BE7dv6F5kGG7TywN78nC
