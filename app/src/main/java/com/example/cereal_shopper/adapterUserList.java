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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;


/*adapter to the list of users that the user can interact with
 *it implements  buttons in each element of the list
**/
public class adapterUserList extends ArrayAdapter<DbUser> {
    private Context mContext;
    private List<DbUser> userList = new ArrayList<>();
    DatabaseHelper db;


    public adapterUserList(@NonNull Context context, @LayoutRes List<DbUser> list) {
        super(context, 0 , list);
        mContext = context;
        userList = list;
        db = new DatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        final int _position = position;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_user_list_item, parent,false);

        final DbUser currentUser = userList.get(position);
        TextView name = (TextView) listItem.findViewById(R.id.user_item_name);
        TextView email = (TextView) listItem.findViewById(R.id.user_item_email);

        TextView balance = (TextView) listItem.findViewById(R.id.user_item_counter);

        //---------------------------different design for the user--------------------------
        if (!currentUser.getName().equals(mContext.getString(R.string.User1))) {
            name.setText(currentUser.getName());
            email.setText(currentUser.getEmail());
        }else {
            name.setText(mContext.getString(R.string.me));
            email.setText("");

        }

        balance.setText( Double.toString(currentUser.getBalance()) );
        //balance.setText( Double.toString(0) );


        ImageView image = listItem.findViewById(R.id.user_item_image);
        if(currentUser.getName().equals(mContext.getString(R.string.User1))) image.setImageResource(R.drawable.matteo);
        if(currentUser.getName().equals(mContext.getString(R.string.user2))) image.setImageResource(R.drawable.marco);
        if(currentUser.getName().equals(mContext.getString(R.string.user3))) image.setImageResource(R.drawable.lucia);


        //------------------------------------reset balance---------------------------------

        Button resetButton = listItem.findViewById(R.id.user_item_counter_btn);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double newB= 0;
                currentUser.setBalance(newB);

                db.updateUser(currentUser);
                ((AddGroupItem) mContext).recreate();
            }
        });

        //--------------------------------------------delete user------------------------------------------------
        LinearLayout item = listItem.findViewById(R.id.user_list_item);

        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final View _v = v;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                if(currentUser.getName().equals(mContext.getString(R.string.User1)))
                    builder1.setMessage(mContext.getString(R.string.uscita_gruppo));
                else
                    builder1.setMessage(mContext.getString(R.string.elimina_utente) );
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        mContext.getString(R.string.si),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ArrayList<Integer> userGroupIds = currentUser.getGroupIds();
                                    userGroupIds.remove( (Integer)((AddGroupItem) mContext).getGroupId() );
                                    currentUser.setGroupId( userGroupIds );
                                    db.updateUser(currentUser);

                                ((AddGroupItem) mContext).recreate();
                                dialog.cancel();
                                //if user != currentUser => delete item
                                //selectedElement = userList.get(_position).getId();

                            }
                        });

                builder1.setNegativeButton(
                        mContext.getString(R.string.no),
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