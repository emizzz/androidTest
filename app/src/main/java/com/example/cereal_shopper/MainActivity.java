package com.example.cereal_shopper;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private adapterGroupList adapter;
    private FloatingActionButton fab;
    Toolbar toolbar;
    ListView listView;
    Global globalApp;
    List<DbGroup> db_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.group_list);

        db = new DatabaseHelper(getApplicationContext());
        globalApp = (Global)getApplicationContext();

        /*---------------------------------FETCH GROUPS---------------------------------*/
        //read database and get all the user's groups

        db = new DatabaseHelper(getApplicationContext());
        db_groups = db.getUserGroups(globalApp.getCurrentUser().getId());

        //link groups to template list
        adapter = new adapterGroupList(this, db_groups);
        listView.setAdapter(adapter);

        findViewById(R.id.addgroup).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        /*---------------------------------CREATE GROUP---------------------------------*/
        //FAB button
        fab = (FloatingActionButton) findViewById(R.id.addgroup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddGroupItem.class);
                startActivity(intent);
            }
        });


    }
}
