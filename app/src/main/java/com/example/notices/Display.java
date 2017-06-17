package com.example.notices;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


public class Display extends Fragment {

    private TextView descriptiontext;
    private TextView titletext;
    String description;
    String title;
    ImageView image;
    FirebaseStorage storage;
    String year;
    String branch;
    View view;
    Button delete;
    Button edit;
    DeleteNotice d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getString("year");
            branch = getArguments().getString("branch");
            title = getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_display, container, false);
        getActivity().setTitle(branch);
        storage = FirebaseStorage.getInstance();
        descriptiontext = (TextView) view.findViewById(R.id.descriptionText);
        titletext = (TextView) view.findViewById(R.id.titleText);
        image = (ImageView) view.findViewById(R.id.imageView);
        delete = (Button) view.findViewById(R.id.deleteButton);
        edit = (Button) view.findViewById(R.id.editButton);
        d = new DeleteNotice();
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            delete.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
        else{
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            d.delete(year,branch,title,description);
                            Fragment welcomeFragment = new Welcome();
                            FragmentManager fm = getFragmentManager();
                            fm.beginTransaction().replace(R.id.mainFragment,welcomeFragment ).addToBackStack(null).commit();

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Delete Notice");
                    dialog.setMessage("Are you sure you want to delete this notice?");
                    dialog.show();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("year",year);
                    bundle.putString("branch",branch);
                    bundle.putString("title",title);
                    bundle.putString("edit","edit");
                    bundle.putString("description",description);
                    Fragment addFragment = new Add();
                    addFragment.setArguments(bundle);
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.mainFragment, addFragment).addToBackStack(null).commit();

                }
            });
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    description = dataSnapshot.child(year)
                            .child(branch)
                            .child(title)
                            .child("description").getValue(String.class);
                    descriptiontext.setText(description);
                    titletext.setText(title);
                    storage.getReference("images/" + description).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri != null){
                                Glide.with(getContext())
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }





}
