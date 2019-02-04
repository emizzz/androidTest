package com.example.cereal_shopper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListaSpesa extends Fragment{
    private RecyclerView groupsListView;
    private RecyclerView.Adapter groupsListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;
    private JSONArray groupsList = new JSONArray();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        JSONObject item111 = new JSONObject();
        try{
            item111.put("type", "product_list");
            item111.put("title", "Latte");
            item111.put("description", "parzialmente scremato");
            item111.put("product_type", "list");      // "list" or "pantry"

            groupsList.put(item111);

        } catch (JSONException e) {
            //log something
        }
        View v = inflater.inflate(R.layout.fragment_lista_spesa, container, false);
        groupsListView = (RecyclerView) v.findViewById(R.id.groups_list);

        mLayoutManager = new LinearLayoutManager(getContext());
        groupsListView.setLayoutManager(mLayoutManager);

        //groupsListAdapter = new List(groupsNames);
        groupsListAdapter = new List(getContext(), groupsList);

        groupsListView.setAdapter(groupsListAdapter);

        fab = (FloatingActionButton) v.findViewById(R.id.addtospesa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),addToSpesa.class);
                getActivity().startActivity(intent);

            }
        });

        return v;
    }
}