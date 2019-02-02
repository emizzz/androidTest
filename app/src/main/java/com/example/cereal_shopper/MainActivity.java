package com.example.cereal_shopper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private RecyclerView groupsListView;
    private RecyclerView.Adapter groupsListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final int REQUEST_CODE_LISTA = 10;


    //private String[] groupsNames = new String[] {"Famiglia", "Conquilini"};


    private JSONArray groupsList = new JSONArray();

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //populate list data


        /*
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
    */


        /*
        //-----------------  USER LIST -----------------
        JSONObject item1 = new JSONObject();
        try{
            item1.put("type", "user_list");
            item1.put("title", "Gianni");
            item1.put("description", "gianni@gianni.it");
            item1.put("photo_path", "photo_path");
            item1.put("counter", "15");

            groupsList.put(item1);

        } catch (JSONException e) {
            //log something
        }*/


        //----------------- PRODUCT LIST -----------------
        JSONObject item1 = new JSONObject();
        try{
            item1.put("type", "product_list");
            item1.put("title", "Latte");
            item1.put("description", "parzialmente scremato");
            item1.put("product_type", "list");      // "list" or "pantry"

            groupsList.put(item1);

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

                startActivityForResult(intent,REQUEST_CODE_LISTA);
                //startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode==REQUEST_CODE_LISTA) {
            Bundle extras= data.getExtras();
            if (extras != null) {
                String name = extras.getString("NAME");
                String category = extras.getString("CATEGORY");
                int quantity = extras.getInt("QUANTITY");
                String note = extras.getString("NOTE");
                Toast.makeText(getApplicationContext(), name + category +  quantity + note, Toast.LENGTH_SHORT).show();
                JSONObject item = new JSONObject();
                try{
                    if (requestCode==REQUEST_CODE_LISTA) {
                        item.put("type", "product_list");
                        item.put("product_type", "list");      // "list" or "pantry"
                    }
                    item.put("title", name);
                    item.put("description", note);

                    groupsList.put(item);

                } catch (JSONException e) {
                    //log something
                }
                groupsListAdapter = new List(this, groupsList);

                groupsListView.setAdapter(groupsListAdapter);
                // and get whatever data you are adding
            }
        }

    }
}
