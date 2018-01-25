package com.example.json.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.json.R;
import com.example.json.utils.Analisis;
import com.example.json.utils.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class Primitiva extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MyTag";
    //public static final String WEB = "http://alumno.mobi/~alumno/superior/alumno/primitiva.json";
    public static final String WEB = "https://portadaalta.mobi/acceso/primitiva.json";
    Button btnPrimitiva;
    TextView txvResulPri;
    RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primitiva);
        btnPrimitiva = (Button) findViewById(R.id.btnPrimitiva);
        btnPrimitiva.setOnClickListener(this);
        txvResulPri = (TextView) findViewById(R.id.txvPrimitiva);
        mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    @Override
    public void onClick(View view) {
        if (view == btnPrimitiva)
            descarga();
    }

    private void descarga() {

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, WEB, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txvResulPri.setText(Analisis.analizarPrimitiva(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txvResulPri.setText(error.hashCode()+":"+error.getMessage());
                    }
                });

        // Set the tag on the request.
        jsObjRequest.setTag(TAG);
        // Set retry policy
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 1));
        // Add the request to the RequestQueue.
        mRequestQueue.add(jsObjRequest);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }


}
