package com.example.cereal_shopper;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private RecyclerView groupsListView;
    private RecyclerView.Adapter groupsListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    //private String[] groupsNames = new String[] {"Famiglia", "Conquilini"};


    private JSONArray groupsList = new JSONArray();

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //populate list data



        // ----------------- GROUP LIST -----------------
        JSONObject item1 = new JSONObject();
        JSONObject item2 = new JSONObject();

        try{
            item1.put("type", "group_list");
            item1.put("title", "Famiglia");
            item2.put("type", "group_list");
            item2.put("title", "Coinquilini");

            groupsList.put(item1);
            groupsList.put(item2);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        //-----------------  USER LIST -----------------
        JSONObject item11 = new JSONObject();
        try{
            item11.put("type", "user_list");
            item11.put("title", "Gianni");
            item11.put("description", "gianni@gianni.it");
            item11.put("photo_path", "photo_path");
            item11.put("counter", "15");

            groupsList.put(item11);

        } catch (JSONException e) {
            //log something
        }


        //----------------- PRODUCT LIST -----------------
        JSONObject item111 = new JSONObject();
        JSONObject item222 = new JSONObject();
        try{
            item111.put("type", "product_list");
            item111.put("title", "Latte");
            item111.put("description", "parzialmente scremato");
            item111.put("product_type", "list");      // "list" or "pantry"
            item222.put("type", "product_list");
            item222.put("title", "Latte");
            item222.put("description", "parzialmente scremato");
            item222.put("product_type", "list");      // "list" or "pantry"
            groupsList.put(item111);
            groupsList.put(item222);

        } catch (JSONException e) {
            //log something
        }


        /*create recycler view*/
        groupsListView = (RecyclerView) findViewById(R.id.groups_list);
        mLayoutManager = new LinearLayoutManager(this);
        groupsListView.setLayoutManager(mLayoutManager);

        //groupsListAdapter = new List(groupsNames);
        groupsListAdapter = new List(this, groupsList);

        groupsListView.setAdapter(groupsListAdapter);




        fab = (FloatingActionButton) findViewById(R.id.addgroup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),group.class);
                startActivity(intent);
            }
        });



    }


}
