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


   // private JSONArray groupsList = new JSONArray();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_dispensa, container, false);
        liste.groupsListViewPant = (RecyclerView) v.findViewById(R.id.groups_list_disp);

        liste.mLayoutManagerPant = new LinearLayoutManager(getContext());
        liste.groupsListViewPant.setLayoutManager(liste.mLayoutManagerPant);

        //groupsListAdapter = new List(groupsNames);
        liste.groupsListAdapterPant = new List(getContext(), liste.pantryList);

        liste.groupsListViewPant.setAdapter(liste.groupsListAdapterPant);


        return v;
    }
    public void prova (){
        liste.groupsListAdapterPant = new List(getContext(), liste.pantryList);

        liste.groupsListViewPant.setAdapter(liste.groupsListAdapterPant);
    }
}