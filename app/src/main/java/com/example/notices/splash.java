package com.example.notices;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    SessionManager s= new SessionManager(getApplicationContext());
//                   if (s.isLoggedIn()||s.isadmin()){
                    if(true){
                        Intent i = new Intent(getApplicationContext(), NoticeList.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(getApplicationContext(), login.class);
                        startActivity(i);
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
}
