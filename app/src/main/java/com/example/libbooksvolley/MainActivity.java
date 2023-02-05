package com.example.libbooksvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue= Volley.newRequestQueue(this);
    }

    public void btnSearchOnClick(View view) {
        EditText edtCat= findViewById(R.id.edtCat);
        ListView list= findViewById(R.id.lstBooks);
        String cat= edtCat.getText().toString();
        String url= "https://10.0.2.2:80/rest/info_json.php?cat" + cat;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<String> books = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                books.add(obj.getString("title"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        String[] arr = new String[books.size()];
                        arr = books.toArray(arr);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                MainActivity.this, android.R.layout.simple_list_item_1, arr);
                        list.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });
        queue.add(request);
    }
    public void btnOpenOnClick(View view) {
        Intent intent= new Intent(this, addActivity.class);
        startActivity(intent);



    }


}