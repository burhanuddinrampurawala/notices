package com.example.notices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    SessionManager s= new SessionManager(getApplicationContext());
                   if (s.isLoggedIn()||s.isadmin()){
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
