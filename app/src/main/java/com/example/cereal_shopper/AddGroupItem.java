package com.example.cereal_shopper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


/*
*Activity for the creation/modification of a group item, depending on how the activity has been created
* the user interacts with the elements to create, choose or change the attributes oft he group
**/
public class AddGroupItem extends AppCompatActivity {
    DatabaseHelper db;
    private adapterUserList adapter;
    ArrayList<AddGroupUser> all_users = new ArrayList<AddGroupUser>();
    ArrayList<DbUser> to_add_users = new ArrayList<DbUser>();
    EditText group_name;
    ListView members_list;

    DbGroup currentGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getApplicationContext());
        Global globalApp = (Global)getApplicationContext();
        setContentView(R.layout.activity_add_group_item);
        group_name = (EditText) findViewById(R.id.new_goup_name);
        members_list = (ListView) findViewById(R.id.group_members_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null){

            /*------------if GROUP_ID is passed this component doesn't create
                            a new group, but instead it modifies an existing one-------------*/
            try{
                currentGroup = db.getGroup( extras.getInt("GROUP_ID") );
                group_name.setText( currentGroup.getTitle() );
            }
            catch(Exception e){
                Log.d("AddGroupItem", "Prev User not picked");
            }

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Modifica gruppo");
            toolbar.setNavigationIcon(R.drawable.left);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


        }else{
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.nuovo_gruppo);
            toolbar.setNavigationIcon(R.drawable.left);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);            }
            });
        }

        //get all the users
        List<DbUser> db_users = db.getUsers();
        DbUser currentUser = globalApp.getCurrentUser();
        boolean userAlreadyInList = false;
        boolean check = true;

        for(DbUser _user : db_users) {
            //autocomplete items
            all_users.add(new AddGroupUser(_user));

            //user that are already members of the group
            if( currentGroup != null ){
                if( _user.getGroupIds().contains( currentGroup.getId() ) ){
                    to_add_users.add(_user);
                }
                if(_user.getId() == currentUser.getId() && check){
                    userAlreadyInList = true;
                    check = false;
                }
            }
        }
        if(!userAlreadyInList){
            to_add_users.add(currentUser);
        }



        //link the selected users to the template's list
        // Create an ArrayAdapter from List
        adapter = new adapterUserList(this, to_add_users);
        members_list.setAdapter(adapter);





        //FAB: add the search dialog and pick new users
        FloatingActionButton fab = findViewById(R.id.AddMember);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat(AddGroupItem.this, getString(R.string.aggiungi_user),
                        "...", null, all_users,
                        new SearchResultListener<AddGroupUser>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog, AddGroupUser item, int position) {
                                //add the selected user to "to_add_users"

                                DbUser _newUser = item.getUser();
                                to_add_users.add(_newUser);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();


                            }
                        }).show();
            }
        });

        //BTN: create the new group with name and members
        Button applyButton = (Button) findViewById(R.id.apply_group_button);
        applyButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _name = group_name.getText().toString();

                if(!_name.equals("")){
                    try{
                        //create the new group (if currentGroup == null)
                        int new_group_id;
                        if(currentGroup == null) {
                            currentGroup = new DbGroup(_name);
                            Long new_group_id_long = db.createGroup(currentGroup);
                            new_group_id = (int) (long) new_group_id_long;
                        }
                        //update the new group (if currentGroup != null)
                        else{
                            new_group_id = currentGroup.getId();
                            currentGroup.setTitle(_name);
                            db.updateGroup(currentGroup);

                        }


                        //update the user's group_ids
                        for(DbUser _added_user : to_add_users){
                            ArrayList<Integer> userGroupIds = _added_user.getGroupIds();
                            if( !userGroupIds.contains(new_group_id) ){
                                userGroupIds.add( new_group_id );
                                _added_user.setGroupId( userGroupIds );
                                db.updateUser(_added_user);
                            }

                        }


                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    catch(Exception e){
                        Toast.makeText(AddGroupItem.this, "Errore interno",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(AddGroupItem.this, getString(R.string.scegli_nome),
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
    }

    public Integer getGroupId(){
        return this.currentGroup.getId();
    }

}
