package com.pt1.laksamedical;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class admin_Listed_items extends AppCompatActivity {
    RecyclerView mRecyclerView;
    admin_item_adapter myAdapter;
    ArrayList<Model> models ;
    public RequestQueue mQueue;
    public static int FLAG = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_items);
        models = new ArrayList<>();
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        mRecyclerView = findViewById(R.id.adminlistedrecy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        models= new ArrayList<>();
        myAdapter = new admin_item_adapter(this,models);
        mRecyclerView.setAdapter(myAdapter);
        getData();

        FloatingActionButton add = findViewById(R.id.additem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),admin_edit_item.class);
                startActivityForResult(i,0);
            }
        });

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        String url = "https://ltiandroid.000webhostapp.com/getData.php";

        JsonObjectRequest ObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray result = response.getJSONArray("result");

                            for (int i = 0; i < result.length(); i++) {
                                JSONObject jo = result.getJSONObject(i);
                                String title = jo.getString("title");
                                String cost = jo.getString("cost");
                                String imgstr = jo.getString("img");
                                int id = Integer.parseInt(jo.getString("item_id"));

                                Model m = new Model();
                                m.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1));
                                m.setCost(cost);
                                m.setId(id);
                                m.setQuant(0);
                                m.setImg(imgstr);

                                models.add(m);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        myAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }) {

        };
        //Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();
        mQueue.add(ObjRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0){
            models.clear();
            getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(FLAG==1){
            models.clear();
            getData();
            FLAG=0;
        }


    }
}