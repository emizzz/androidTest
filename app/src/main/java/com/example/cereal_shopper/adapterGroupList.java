package com.example.cereal_shopper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class adapterGroupList extends ArrayAdapter<DbGroup> {
    private DatabaseHelper db;
    private Context mContext;
    private List<DbGroup> groupList = new ArrayList<>();

    public adapterGroupList(@NonNull Context context, @LayoutRes List<DbGroup> list) {
        super(context, 0 , list);
        db = new DatabaseHelper(context);
        mContext = context;
        groupList = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
         if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_group_list_item, parent,false);

        final DbGroup currentGroup = groupList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.group_item_name);
        name.setText(currentGroup.getTitle() + " " + currentGroup.getId());


        //--------------------------------------------modify group------------------------------------------------
        ImageButton modify_group = (ImageButton) listItem.findViewById(R.id.group_item_icon2);

        modify_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(),group.class);
                //startActivity(new Intent(getApplicationContext(), AddGroupItem.class));

                Intent intent=new Intent(getContext(), AddGroupItem.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("GROUP_ID", groupList.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });

        //--------------------------------------------delete from group------------------------------------------------
        ImageButton delete_from_group = (ImageButton) listItem.findViewById(R.id.group_item_icon1);

        delete_from_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View _v = v;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Sei sicuro di voler uscire dal gruppo?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sì",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Global globalApp = (Global)_v.getContext().getApplicationContext();
                                //remove the group id from the current logged user
                                DbUser currentUser = globalApp.getCurrentUser();

                                if( currentUser.getGroupIds().contains( currentGroup.getId() ) ){
                                    ArrayList<Integer> user_groups = currentUser.getGroupIds();
                                    user_groups.remove( new Integer(currentGroup.getId()) );
                                    currentUser.setGroupId( user_groups );
                                    long result = db.updateUser(currentUser);
                                    if(result > 0){
                                        Intent intent=new Intent(getContext(), MainActivity.class);
                                        _v.getContext().startActivity(intent);
                                        Log.d("adapterGroupList", "User deleted from group");
                                    }
                                }

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return listItem;


    }

}

