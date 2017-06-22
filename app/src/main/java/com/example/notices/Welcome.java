package com.example.notices;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class Welcome extends Fragment {

    protected Boolean Loggedin = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Welcome");
        if (getArguments() != null) {
            Loggedin = getArguments().getBoolean("Loggedin");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        if(Loggedin){
            Intent i = new Intent(view.getContext(),Start.class);
            startActivity(i);
        }
        return view;
    }



}
