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

    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_lista_spesa, container, false);
        liste.groupsListViewList = (RecyclerView) v.findViewById(R.id.groups_list);

        liste.mLayoutManagerList = new LinearLayoutManager(getContext());
        liste.groupsListViewList.setLayoutManager(liste.mLayoutManagerList);

        //groupsListAdapter = new List(groupsNames);

        liste.groupsListAdapterList = new List(getContext(),liste.listList );
        liste.groupsListViewList.setAdapter( liste.groupsListAdapterList);

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
    public void prova (){
        liste.groupsListAdapterList = new List(getContext(), liste.listList);

        liste.groupsListViewList.setAdapter( liste.groupsListAdapterList);
    }
}