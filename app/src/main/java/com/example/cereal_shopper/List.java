package com.example.cereal_shopper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class List extends RecyclerView.Adapter {
    private JSONArray data;
    private Context mContext;
    private int size;
    private static final int REQUEST_CODE_GROUP = 30;
    private static final int REQUEST_CODE_LISTA = 10;
    private static final int REQUEST_CODE_DISPENSA = 20;
    private DbHandler dbHandler;

    // GROUP LIST VIEWHOLDER
    public static class GroupListViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageButton userbtn;
        public ImageButton delbtn;

        public View mView;
        public GroupListViewHolder(View v) {
            super(v);
            mView = v;
            title = (TextView) mView.findViewById(R.id.group_item_name);


            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),liste.class);
                    v.getContext().startActivity(intent);
                }
            });
            userbtn = (ImageButton) mView.findViewById(R.id.group_item_icon2);
            userbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),group.class);
                    String name=(String) ((TextView) mView.findViewById(R.id.group_item_name)).getText();
                    intent.putExtra("NAME", name);
                    ((Activity) v.getContext()).startActivityForResult(intent,REQUEST_CODE_GROUP);
                }
            });
            delbtn = (ImageButton) mView.findViewById(R.id.group_item_icon1);
        }
    }


    // USER LIST VIEWHOLDER
    public static class UserListViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView photo;
        public TextView counter;
        public Button counter_btn;

        public View mView;
        public UserListViewHolder(View v) {
            super(v);
            mView = v;
            title = mView.findViewById(R.id.user_item_name);
            description = mView.findViewById(R.id.user_item_email);
            photo = mView.findViewById(R.id.user_item_image);
            counter = mView.findViewById(R.id.user_item_counter);
            counter_btn = mView.findViewById(R.id.user_item_counter_btn);

            counter_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //here goes the conter_btn logic
                }
            });
        }
    }

    // PRODUCT LIST VIEWHOLDER
    public static class ProductListViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView icon;
        public View mView;
        public ImageButton delbtn;

        public ProductListViewHolder(View v) {

            super(v);
            mView = v;
            title =  mView.findViewById(R.id.product_item_name);
            description = mView.findViewById(R.id.product_item_description);
            icon = mView.findViewById(R.id.product_item_image);

           delbtn = (ImageButton) mView.findViewById(R.id.product_item_icon1);

            delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),addItem.class);
                    String name=(String) ((TextView) mView.findViewById(R.id.product_item_name)).getText();
                    intent.putExtra("NAME", name);
                    ((Activity) v.getContext()).startActivityForResult(intent,REQUEST_CODE_DISPENSA);
                }
            });

        }
    }


    // Adapter Constructor
    public List(Context context, JSONArray _data) {

        this.data = _data;
        this.mContext = context;
        this.size = _data.length();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view;

        switch (viewType) {
            case 0:     //0 == group_list
                view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_group_list_item, parent, false);
                return new GroupListViewHolder(view);

            case 1:    //1 == user_list
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_item, parent, false);
                return new UserListViewHolder(view);

            case 2:    //2 == product_list
                view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_list_item, parent, false);
                return new ProductListViewHolder(view);

            default:
                return null;

        }

    }

    @Override
    public int getItemViewType(int i) {
        String type = "";

        try {
            type = data.getJSONObject(i).getString("type");

        } catch (JSONException e) {
            return -1;
        }
        switch (type) {
            case "group_list":
                return 0;
            case "user_list":
                return 1;
            case "product_list":
                return 2;
            default:
                return -1;
        }


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int i) {
        System.out.println(i);
        String type = "";

        try {
            type = data.getJSONObject(i).getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.e("---", type);

        switch (type) {
            case "group_list":

                try {
                    ((GroupListViewHolder) holder).title.setText(data.getJSONObject(i).getString("title"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ((GroupListViewHolder) holder).delbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setTitle("Eliminare gruppo?");
                        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(holder.getAdapterPosition());
                                ((MainActivity)mContext).prova();
                                //notifyDataSetChanged();
                                //notifyItemRemoved(holder.getAdapterPosition());
                               // notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount()-1);
                                dialog.cancel();

                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }



                });


                break;

            case "user_list":
                try {
                    String price = String.format(this.mContext.getResources().getString(R.string.prices),
                            data.getJSONObject(i).getString("counter"));

                    ((UserListViewHolder) holder).title.setText(data.getJSONObject(i).getString("title"));
                    ((UserListViewHolder) holder).description.setText(data.getJSONObject(i).getString("description"));
                    //((UserListViewHolder) holder).photo.setImageDrawable();
                    ((UserListViewHolder) holder).counter.setText(price);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case "product_list":
                try {

                    ((ProductListViewHolder) holder).title.setText(data.getJSONObject(i).getString("title"));
                    ((ProductListViewHolder) holder).description.setText(data.getJSONObject(i).getString("description"));

                    if(data.getJSONObject(i).getString("product_type").equals("list")){
                        //((ProductListViewHolder) holder).icon.setImageDrawable();
                    }
                    if(data.getJSONObject(i).getString("product_type").equals("pantry")){
                        //((ProductListViewHolder) holder).icon.setImageDrawable();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        //FINEPROVA

    }

    // Return the size of your data (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.size;
    }
    public void removeItem(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }


}
