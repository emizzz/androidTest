package com.example.cereal_shopper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private adapterGroupList adapter;


    /*private static final int REQUEST_CODE_LISTA = 10;
    private static final int REQUEST_CODE_DISPENSA = 20;
    private static final int REQUEST_CODE_GROUP = 30;*/

    private FloatingActionButton fab;
    Toolbar toolbar;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.group_list);

        db = new DatabaseHelper(getApplicationContext());


        /*---------------------------------FETCH GROUPS---------------------------------*/
        //read database and get all the user's groups
        Global globalApp = (Global)getApplicationContext();
        db = new DatabaseHelper(getApplicationContext());
        ArrayList<DbGroup> groups = new ArrayList<>();
        List<DbGroup> db_groups = db.getUserGroups(globalApp.getCurrentUser().getId());

        //insert groups in the adapter "adapterGroupList"
        adapter = new adapterGroupList(this, db_groups);
        listView.setAdapter(adapter);


        /*---------------------------------MODIFY GROUP---------------------------------*/
        /*
        ImageButton modify_group = (ImageButton)findViewById(R.id.group_item_icon1);
        modify_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ImageButton", "Clicked!");
            }
        });
        */


        /*---------------------------------CREATE GROUP---------------------------------*/
        //FAB button
        fab = (FloatingActionButton) findViewById(R.id.addgroup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddGroupItem.class);
                startActivity(intent);
                //startActivityForResult(intent,REQUEST_CODE_GROUP);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        /*
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
                    item.put("type", "product_list");
                    item.put("product_type", "list");      // "list" or "pantry"
                    item.put("title", name);
                    item.put("description", note);

                    groupsList.put(item);

                } catch (JSONException e) {
                    //log something
                }

                groupsListAdapter = new CustomList(this, groupsList);

                groupsListView.setAdapter(groupsListAdapter);

                // and get whatever data you are adding
            }
        }
        if (requestCode==REQUEST_CODE_DISPENSA) {
            Bundle extras= data.getExtras();
            if (extras != null) {
                String name = extras.getString("NAME");
                String category = extras.getString("CATEGORY");
                int quantity = extras.getInt("QUANTITY");
                int weight = extras.getInt("WEIGHT");
                String price = extras.getString("PRICE");
                String expiration = extras.getString("EXPIRATION");
                String note = extras.getString("NOTE");
                JSONObject item = new JSONObject();
                try{
                    item.put("type", "product_list");
                    item.put("product_type", "pantry");      // "list" or "pantry"
                    item.put("title", name);
                    item.put("description", note);

                    groupsList.put(item);

                } catch (JSONException e) {
                    //log something
                }

                groupsListAdapter = new CustomList(this, groupsList);

                groupsListView.setAdapter(groupsListAdapter);
                // and get whatever data you are adding
            }
        }

        if (requestCode==REQUEST_CODE_GROUP) {
            Bundle extras= data.getExtras();
            if (extras != null) {
                String newname = extras.getString("NEWNAME");
                String oldname = extras.getString("OLDNAME");
                //Toast.makeText(getApplicationContext(),oldname,Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getApplicationContext(),newname,Toast.LENGTH_SHORT).show();

                try {
                    int ii=0;
                    boolean p=true;
                    while((ii<groupsList.length())&& (p)) {
                        if(groupsList.getJSONObject(ii).getString("title").equals(oldname)){
                            p=false;
                        }else
                             ii++;
                    }
                    if(p){

                        JSONObject item = new JSONObject();

                        try{
                            item.put("type", "group_list");
                            item.put("title", newname);


                            groupsList.put(item);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //dbHandler.updateHandler("group_list",oldname)
                    }
                    else {
                        JSONObject nuovo = groupsList.getJSONObject(ii);
                        nuovo.put("title", newname);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

           //     dbHandler.addHandler("group_list",newname);
                groupsListAdapter = new CustomList(this, groupsList);

                groupsListView.setAdapter(groupsListAdapter);

            }

        }*/


    }

    public void prova (){
        //groupsListAdapter = new CustomList(this, groupsList);

        //groupsListView.setAdapter(groupsListAdapter);
    }
}
