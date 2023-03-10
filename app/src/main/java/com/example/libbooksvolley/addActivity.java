package com.example.libbooksvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addActivity extends AppCompatActivity {
    private EditText edtTitle, edtCategory, edtPages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtTitle= findViewById(R.id.edtTitle);
        edtCategory= findViewById(R.id.edtCat);
        edtPages= findViewById(R.id.edtPages);
    }

    public void btnAddOnClick(View view) {
        String bookTitle= edtTitle.getText().toString();
        String bookPages= edtPages.getText().toString();
        String bookCat= edtCategory.getText().toString();

        addBook(bookTitle, bookCat, bookPages);

    }

    private void addBook(String title, String category, String pages) {
        String url = "http://10.0.2.2:80/rest/addbook_json.php";
        RequestQueue queue = Volley.newRequestQueue(addActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(addActivity.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(addActivity.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                params.put("title", title);
                params.put("category", category);
                params.put("pages", pages);


                return params;
            }
        };

        queue.add(request);

    }

}