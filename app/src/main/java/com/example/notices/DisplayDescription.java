package com.example.notices;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayDescription extends Activity {

    private TextView descriptiontext;
    String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_description);
        descriptiontext = (TextView) findViewById(R.id.descriptionText);
//        Intent i = getIntent();
//        description = i.getStringExtra("description");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            description = extras.getString("description");
            // and get whatever type user account id is
        }
        descriptiontext.setText(description);
    }
}
