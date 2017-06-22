package com.example.notices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Add extends Fragment {

    private  String oldTitle;
    private  String oldYear;
    private  String oldBranch;
    private String branch;
    private String year;
    private  String edit = null;
    FirebaseDatabase fdb;
    private DatabaseReference ref;
    private EditText titleText;
    private EditText descriptionText;
    private String title;
    private String description;
    private Button add;
    private ProgressDialog pDialog;
    private int i;
    private int id;
    private Button camera;
    private Button gallery;
    private Button file;
    FirebaseStorage storage;
    boolean result;
    ImageView displayimage;
    private TextView error;
    View focusview;
    View view;
    String oldDescription;

    public Add() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            edit = getArguments().getString("edit");
            oldYear = getArguments().getString("year");
            oldBranch = getArguments().getString("branch");
            oldTitle = getArguments().getString("title");
            oldDescription = getArguments().getString("description");
            getActivity().setTitle("Edit");
        }
        else {
            getActivity().setTitle("Add");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);
        error = (TextView) view.findViewById(R.id.error);
        focusview = getView();

        result = Utility.checkPermission(view.getContext());

        storage = FirebaseStorage.getInstance();

        displayimage = (ImageView)view.findViewById(R.id.displayimage);

        fdb = FirebaseDatabase.getInstance();
        ref = fdb.getReference();

        dropDownYear();
        dropDownBranch();
        add = (Button) view.findViewById(R.id.addButton);

        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        titleText = (EditText) view.findViewById(R.id.title);
        descriptionText = (EditText) view.findViewById(R.id.description);
        camera = (Button) view.findViewById(R.id.camera);
        gallery = (Button) view.findViewById(R.id.gallery);
        file = (Button) view.findViewById(R.id.file);
        if(edit == "edit")
            add.setText("Edit");
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
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (edit == "edit") {
                            add(1);
                        }
                        else{
                            add(0);

                        }
                    }

                }
        );
        return view;
    }
    private void add(int j){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            title = titleText.getText().toString();
            description = descriptionText.getText().toString();
            if(title.length() == 0) {
                titleText.setError("Enter title");
                titleText.requestFocus();
            }
            else if (description.length() == 0){
                descriptionText.setError("Enter Description");
                descriptionText.requestFocus();
            }
            else {
                ref.child(year).child(branch).child(title).child("description").setValue(description);
                displayimage.setDrawingCacheEnabled(true);
                displayimage.buildDrawingCache();
                Bitmap bitmap = displayimage.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if(bitmap != null)
                {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference images = storage.getReference("images/"+ description);
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
                if(j == 1){
                    DeleteNotice d = new DeleteNotice();
                    d.delete(oldYear,oldBranch,oldTitle,oldDescription);
                    jump();
                }
                else
                    jump();
            }


        }
        else{
            Toast.makeText(getContext(),"yow will have to Sign in first",Toast.LENGTH_LONG);
        }
    }
    private void jump(){
        Fragment fragment = new Branch();
        Bundle bundle = new Bundle();
        bundle.putString("year",year);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.mainFragment,fragment).commit();
    }

    private void dropDownYear() {
        final Spinner spinnerYear = (Spinner) view.findViewById(R.id.selectYear);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) spinnerYear.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                error.setError("Select the year");
                focusview = error;
            }
        });
    }
    private void dropDownBranch() {
        final Spinner spinnerBranch = (Spinner) view.findViewById(R.id.selectBanch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch = (String) spinnerBranch.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                error.setError("Select the branch");
                focusview = error;
            }
        });
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
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        displayimage.setImageBitmap(bm);
    }



}
