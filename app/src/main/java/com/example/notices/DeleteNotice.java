package com.example.notices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



/**
 * Created by admin on 24/09/2016.
 */
public class DeleteNotice extends Activity {
    private ProgressDialog pDialog;
    private Context context;
    private static final String TAG = login.class.getSimpleName();
    public DeleteNotice (int id, Context context){
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        this.context = context;
        delete (id);
    }
    private void delete(final int id) {
        final DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Iterable<DataSnapshot> a = dataSnapshot.getChildren();
                int j = 0;
                for (DataSnapshot m : a) {
                    if (id == j) {
                        String uid = m.getKey();
                        mPostReference.child(uid).child("head").removeValue();
                        mPostReference.child(uid).child("title").removeValue();
                        mPostReference.child(uid).child("description").removeValue();
                        break;
                    }
                    j++;
                }
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
}
