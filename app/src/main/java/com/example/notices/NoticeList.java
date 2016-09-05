package com.example.notices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoticeList extends AppCompatActivity {

    private SQLiteHandler db;
    private Button logout;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        session.setLogin(false);

                        db.deleteUsers();

                        // Launching the login activity
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}
