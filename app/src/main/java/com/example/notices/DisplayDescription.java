package com.example.notices;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DisplayDescription extends Activity {

    private TextView descriptiontext;
    private TextView titletext;
    String description;
    String title;
    ImageView image;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_description);
        storage = FirebaseStorage.getInstance();
        descriptiontext = (TextView) findViewById(R.id.descriptionText);
        titletext = (TextView) findViewById(R.id.titleText);
        image = (ImageView) findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            description = extras.getString("description");
            title = extras.getString("title");
            // and get whatever type user account id is
        }
        descriptiontext.setText(description);
        titletext.setText(title);
         storage.getReference("images/" + title).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if(uri != null){
                    Glide.with(DisplayDescription.this)
                            .load(uri)
                            .into(image);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }
}
