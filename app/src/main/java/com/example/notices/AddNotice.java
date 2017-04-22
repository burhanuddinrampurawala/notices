package com.example.notices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class AddNotice extends Activity {
    FirebaseDatabase fdb;
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
    private Button camera;
    private Button gallery;
    private Button file;
    FirebaseStorage storage;
    boolean result;
    ImageView displayimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

         result=Utility.checkPermission(AddNotice.this);

        storage = FirebaseStorage.getInstance();

        displayimage = (ImageView)findViewById(R.id.displayimage);

        fdb = FirebaseDatabase.getInstance();
        ref = fdb.getReference();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        titleText = (EditText) findViewById(R.id.title);
        descriptionText = (EditText) findViewById(R.id.description);
        headtext = (EditText)findViewById(R.id.head);
        add = (Button) findViewById(R.id.addButton);
        camera = (Button) findViewById(R.id.camera);
        gallery = (Button) findViewById(R.id.gallery);
        file = (Button) findViewById(R.id.file);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result){

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select File"),2);
                }
            }
        });

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

                                displayimage.setDrawingCacheEnabled(true);
                                displayimage.buildDrawingCache();
                                Bitmap bitmap = displayimage.getDrawingCache();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                if(bitmap != null)
                                {
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] data = baos.toByteArray();
                                    StorageReference images = storage.getReference("images/"+ title);
                                    UploadTask uploadTask = images.putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle unsuccessful uploads
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                            //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                        }
                                    });
                                }

                                Intent intent = new Intent(AddNotice.this, NoticeList.class);
                                startActivity(intent);
                            }
                        }

                    }
        );

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1)
                onCaptureImageResult(data);
            else if (requestCode == 2)
                onSelectFromGalleryResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayimage.setImageBitmap(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        displayimage.setImageBitmap(bm);
    }

}
