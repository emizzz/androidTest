package com.example.cereal_shopper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dispensa extends Fragment{
    private RecyclerView groupsListView;
    private RecyclerView.Adapter groupsListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private JSONArray groupsList = new JSONArray();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        JSONObject item111 = new JSONObject();
        JSONObject item222 = new JSONObject();
        try{
            item111.put("type", "product_list");
            item111.put("title", "Caffe");
            item111.put("description", "mocha");
            item111.put("product_type", "list");      // "list" or "pantry"
            item222.put("type", "product_list");
            item222.put("title", "Pasta");
            item222.put("description", "Spaghetti");
            item222.put("product_type", "list");      // "list" or "pantry"
            groupsList.put(item111);
            groupsList.put(item222);

        } catch (JSONException e) {
            //log something
        }
        View v = inflater.inflate(R.layout.fragment_dispensa, container, false);
        groupsListView = (RecyclerView) v.findViewById(R.id.groups_list_disp);

        mLayoutManager = new LinearLayoutManager(getContext());
        groupsListView.setLayoutManager(mLayoutManager);

        //groupsListAdapter = new List(groupsNames);
        groupsListAdapter = new List(getContext(), groupsList);

        groupsListView.setAdapter(groupsListAdapter);


        return v;
    }
}