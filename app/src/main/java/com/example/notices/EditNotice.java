package com.example.notices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class EditNotice extends Activity {
    private ProgressDialog pDialog;
    private Context context;
    private static final String TAG = login.class.getSimpleName();
    public EditNotice (String uid, String title, String description, Context context){
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        this.context = context;
        edit (uid, title, description);
    }
    private void edit(final String uid, final String title, final String description) {
        // Tag used to cancel the request
        String tag_string_req = "req_delete";

        pDialog.setMessage("updating....");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDIT_NOTICES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "update Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String errorMsg = jObj.getString("error_msg");
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        // Error in login. Get the error message
                        Toast.makeText(context,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "update Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uid);
                params.put("title", title);
                params.put("description", description);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance(context).addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
