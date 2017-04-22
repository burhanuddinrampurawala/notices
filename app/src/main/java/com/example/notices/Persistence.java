package com.example.notices;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by burha on 4/22/2017.
 */

public class Persistence extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
