package com.project.stopdistraction.common;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DataHelper {

    private static DataHelper dataHelper;

    private String departName;

    public DataHelper() {
    }

    public static DataHelper getInstance() {
        if(dataHelper == null){
            dataHelper = new DataHelper();
        }
        return dataHelper;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String ApiCall(String url, Context context, String responseString){

        StringRequest stringRequest = new StringRequest(config.DATA_URL+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                responseString = response;
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                Toast.makeText(context, "Oops. Request Timeout! Please Try Again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return responseString;
    }
}
