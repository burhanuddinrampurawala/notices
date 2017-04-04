package com.example.notices;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayDescription extends Activity {

    private TextView descriptiontext;
    private TextView titletext;
    String description;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_description);
        descriptiontext = (TextView) findViewById(R.id.descriptionText);
        titletext = (TextView) findViewById(R.id.titleText);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            description = extras.getString("description");
            title = extras.getString("title");
            // and get whatever type user account id is
        }
        descriptiontext.setText(description);
        titletext.setText(title);
    }
}
