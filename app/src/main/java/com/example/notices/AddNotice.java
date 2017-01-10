package com.example.notices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.R.attr.key;

public class AddNotice extends Activity {
    //private DatabaseReference ref;
    private EditText titleText;
    private EditText descriptionText;
    private String title;
    private String description;
    private Button add;
    private EditNotice edit;
    private ProgressDialog pDialog;
    private static final String TAG = login.class.getSimpleName();
    private int i;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        titleText = (EditText) findViewById(R.id.title);
        descriptionText = (EditText) findViewById(R.id.description);
        add = (Button) findViewById(R.id.addButton);
        title = titleText.getText().toString();
        description = descriptionText.getText().toString();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            add.setText("Edit");
            uid = extras.getString("uid");
            i = 1;
        }
        add.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (i == 1) {
                                title = titleText.getText().toString();
                                description = descriptionText.getText().toString();
                                //sendToken();
                                edit = new EditNotice(uid, title, description, AddNotice.this);
                                Intent intent = new Intent(AddNotice.this, NoticeList.class);
                                startActivity(intent);
                            }
                            else{
                                title = titleText.getText().toString();
                                description = descriptionText.getText().toString();
                                addNotice(title, description);
                                Intent i = new Intent(AddNotice.this, NoticeList.class);
                                startActivity(i);
                            }
                        }

                    }
        );

    }

    private void addNotice(final String title, final String description) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("ADDING....");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_NOTICES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v(TAG, "add Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // notice successfully stored in MySQL
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),NoticeList.class);
                        startActivity(i);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "adding Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("description", description);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance(getApplicationContext()).addToRequestQueue(strReq, tag_string_req);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
