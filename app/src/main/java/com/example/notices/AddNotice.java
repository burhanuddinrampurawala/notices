package com.example.notices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNotice extends AppCompatActivity {

    private EditText titleText;
    private EditText descriptionText;
    private String title;
    private String description;
    private Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        titleText = (EditText) findViewById(R.id.title);
        descriptionText = (EditText) findViewById(R.id.description);
        add = (Button) findViewById(R.id.addButton);
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        title = titleText.getText().toString();
                        description = descriptionText.getText().toString();

                    }
                }
        );

    }
}
