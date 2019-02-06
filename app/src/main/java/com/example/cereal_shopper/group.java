package com.example.cereal_shopper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class group extends AppCompatActivity {
    ImageButton changeNameBtn;
    FloatingActionButton fbtn;
    Button updateBtn;
    private RecyclerView groupsListView;
    private RecyclerView.Adapter groupsListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONArray groupsList = new JSONArray();
    private SearchView sV;
    private String realName;
    private String oldName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        if (getIntent().getStringExtra("NAME")!=null) {
            realName=getIntent().getStringExtra("NAME");
            oldName=realName;
            TextView name = findViewById(R.id.Name);
            name.setText(realName);
        }
        changeNameBtn= (ImageButton) findViewById(R.id.modifyName);


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edittext = new EditText(getApplicationContext());
                alert.setTitle("Modifica nome gruppo");
                alert.setView(edittext);

                alert.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        //Editable YouEditTextValue = edittext.getText();
                        //OR
                        String newName = edittext.getText().toString();
                        TextView name= findViewById(R.id.Name);
                        name.setText(newName);
                        realName=newName;

                    }
                });
                alert.show();
            }
        });
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
        groupsListView = (RecyclerView) findViewById(R.id.groups_list);
        mLayoutManager = new LinearLayoutManager(this);
        groupsListView.setLayoutManager(mLayoutManager);

        //groupsListAdapter = new List(groupsNames);
      //  groupsListAdapter = new List(this, groupsList);

       // groupsListView.setAdapter(groupsListAdapter);

        fbtn= (FloatingActionButton) findViewById(R.id.AddMember);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edittext = new EditText(getApplicationContext());
                alert.setTitle("Aggiungi nuovo membro");
                alert.setIcon(R.drawable.search);
                alert.setView(edittext);

                alert.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        //Editable YouEditTextValue = edittext.getText();
                        //OR
                        String YouEditTextValue = edittext.getText().toString();
                    }
                });
                alert.show();
            }
        });
        updateBtn= (Button) findViewById(R.id.update_button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent ();
                data.putExtra("NEWNAME",realName);
                data.putExtra("OLDNAME",oldName);
                setResult(Activity.RESULT_OK,data);
                finish();
            }
        });
    }


}

