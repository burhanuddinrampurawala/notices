package com.example.notices;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.R.attr.key;

/**
 * Created by admin on 06/10/2016.
 */

public class FcmToken extends FirebaseInstanceIdService {
    private static final String TAG = login.class.getSimpleName();
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG, "add Response: " + token);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("FCM_TOKEN",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOKEN",token);
        editor.commit();
    }
}
