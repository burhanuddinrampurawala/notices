package com.example.notices;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Branch extends Fragment {
    private String year;

    private ExpandableListView listView;
    private MyAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    List<String> list;
    View view;

     private String [] header;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getString("year");
        }
        getActivity().setTitle(year);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_branch, container, false);
        listView = (ExpandableListView)view.findViewById(R.id.branchListView);
        header =   getResources().getStringArray(R.array.branch);
        initData();
        listAdapter = new MyAdapter(getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Fragment displayfragment = new Display();
                Bundle bundle = new Bundle();
                bundle.putString("year",year);
                bundle.putString("branch",header[groupPosition]);
                bundle.putString("title", listHash.get(header[groupPosition]).get(childPosition));
                Log.i("notice",bundle.toString());
                displayfragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.mainFragment,displayfragment).commit();
                return true;
            }
        });
        return view;
    }


    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        for(String title: header){
            listDataHeader.add(title);
        }
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference();
        mPostReference.keepSynced(true);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot != null){
                    DataSnapshot a = dataSnapshot.child(year);
                    for(int i = 0; i<8; i++){
                        Iterable<DataSnapshot> d = a.child(header[i]).getChildren();
                        list = new ArrayList<>();
                        for(DataSnapshot data : d){
                            list.add(data.getKey());
                        }
                        Log.i("notice",list.toString());
                        listHash.put(header[i], list);
                    }
                    Log.i("notice",listHash.toString());
                    Log.i("notice",listDataHeader.toString());

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
