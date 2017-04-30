package com.example.notices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


/**
 * Created by admin on 24/09/2016.
 */
public class DeleteNotice extends Activity {
    private ProgressDialog pDialog;
    private Context context;
    FirebaseStorage storage;
    public DeleteNotice (int id, Context context){
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        this.context = context;
        delete (id);
    }
    private void delete(final int id) {
        final DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Iterable<DataSnapshot> a = dataSnapshot.getChildren();
                int j = 0;
                for (DataSnapshot m : a) {
                    if (id == j) {
                       final String uid = m.getKey();
                        String s = m.child("title").getValue(String.class);
                        Log.i("notice",s);
                        storage.getReference("images/" + s).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Uh-oh, an error occurred!
                            }
                        });
                        mPostReference.child(uid).removeValue();
                        break;
                    }
                    j++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("notice", "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        };
        mPostReference.addValueEventListener(postListener);
    }
}
