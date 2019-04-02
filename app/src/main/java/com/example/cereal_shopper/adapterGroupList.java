package com.example.cereal_shopper;


import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class adapterGroupList extends ArrayAdapter<DbGroup> {

    private Context mContext;
    private List<DbGroup> groupList = new ArrayList<>();

    public adapterGroupList(@NonNull Context context, @LayoutRes List<DbGroup> list) {
        super(context, 0 , list);
        mContext = context;
        groupList = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_group_list_item, parent,false);

        DbGroup currentGroup = groupList.get(position);

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
        return listItem;
    }

}