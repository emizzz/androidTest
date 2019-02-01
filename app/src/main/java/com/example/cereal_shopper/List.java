package com.example.cereal_shopper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class List extends RecyclerView.Adapter<List.MyViewHolder> {
    private String[] data;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  name;
        public View mView;
        public MyViewHolder(View v) {
            super(v);
            mView = v;
            name = (TextView) mView.findViewById(R.id.item_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of data)
    public List(String[] _data) {
        data = _data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public List.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        // - get element from your data at this position
        // - replace the contents of the view with that element

        //holder.mView.setText(data[position]);
        holder.name.setText(data[i]);
    }

    // Return the size of your data (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.length;
    }
}
