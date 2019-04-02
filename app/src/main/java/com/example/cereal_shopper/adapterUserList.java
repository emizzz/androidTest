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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class adapterUserList extends ArrayAdapter<DbUser> {
    private Context mContext;
    private List<DbUser> userList = new ArrayList<>();

    public adapterUserList(@NonNull Context context, @LayoutRes List<DbUser> list) {
        super(context, 0 , list);
        mContext = context;
        userList = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        final int _position = position;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_user_list_item, parent,false);

        DbUser currentUser = userList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.user_item_name);
        TextView email = (TextView) listItem.findViewById(R.id.user_item_email);
        TextView balance = (TextView) listItem.findViewById(R.id.user_item_counter);

        name.setText(currentUser.getName() + currentUser.getSerializedGroupId());
        email.setText(currentUser.getEmail());
        balance.setText( Integer.toString(currentUser.getBalance()) );


        //--------------------------------------------delete user------------------------------------------------
        // TODO: delete item
        LinearLayout item = listItem.findViewById(R.id.user_list_item);

        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final View _v = v;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Vuoi eliminare questo utente dal gruppo?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sì",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                //if user != currentUser => delete item
                                //selectedElement = userList.get(_position).getId();

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
                return true;
            }
        });

        return listItem;
    }

}