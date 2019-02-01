package com.example.cereal_shopper;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView groupsListView;
    private RecyclerView.Adapter groupsListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] groupsNames = new String[] {"Famiglia", "Conquilini"};

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*create recycler view*/
        groupsListView = (RecyclerView) findViewById(R.id.groups_list);
        mLayoutManager = new LinearLayoutManager(this);
        groupsListView.setLayoutManager(mLayoutManager);

        groupsListAdapter = new List(groupsNames);
        groupsListView.setAdapter(groupsListAdapter);



        /*fab button
        fab = findViewById(R.id.addgroup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),liste.class);
                startActivity(intent);
            }
        });*/



    }


}
