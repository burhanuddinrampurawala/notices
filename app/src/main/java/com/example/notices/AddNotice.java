package com.example.notices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.*;


import java.util.*;

public class AddNotice extends Activity {
    private DatabaseReference ref;
    private EditText titleText;
    private EditText descriptionText;
    private EditText headtext;
    private String title;
    private String description;
    private String head;
    private Button add;
    private ProgressDialog pDialog;
    private static final String TAG = login.class.getSimpleName();
    private int i;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        ref = FirebaseDatabase.getInstance().getReference();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        titleText = (EditText) findViewById(R.id.title);
        descriptionText = (EditText) findViewById(R.id.description);
        headtext = (EditText)findViewById(R.id.head);
        add = (Button) findViewById(R.id.addButton);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            add.setText("Edit");
            id = extras.getInt("id");
            i = 1;
        }
        add.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (i == 1) {
                                title = titleText.getText().toString();
                                description = descriptionText.getText().toString();
                                head = headtext.getText().toString();
                                final DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference();
                                ValueEventListener postListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // Get Post object and use the values to update the UI
                                        Iterable<DataSnapshot> a = dataSnapshot.getChildren();
                                        int j = 0;
                                        for (DataSnapshot m : a){
                                            if(id == j){
                                                String uid = m.getKey();
                                                mPostReference.child(uid).child("head").setValue(head);
                                                mPostReference.child(uid).child("title").setValue(title);
                                                mPostReference.child(uid).child("description").setValue(description);
                                                break;
                                            }
                                            j++;
                                        }
                                        Intent intent = new Intent(AddNotice.this, NoticeList.class);
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        // Getting Post failed, log a message
                                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                                        // ...
                                    }

                                };
                                mPostReference.addValueEventListener(postListener);
                            }
                            else{
                                title = titleText.getText().toString();
                                description = descriptionText.getText().toString();
                                head = headtext.getText().toString();
                                HashMap<String, String> send = new HashMap<String, String>();
                                send.put("head",head);
                                send.put("title", title);
                                send.put("description",description);
                                String  uuid = ref.push().getKey();
                                ref.child(uuid).setValue(send);
                                //addNotice(title, description);
                                Intent intent = new Intent(AddNotice.this, NoticeList.class);
                                startActivity(intent);
                            }
                        }

                    }
        );

    }

}
