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
import android.widget.ListView;

import java.util.ArrayList;

public class Pantry extends Fragment{
    private DatabaseHelper db;
    private Global globalApp;
    private DbUser currentUser;
    private ArrayList<DbProduct> products;
    private adapterProductList adapter;
    private Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dispensa, container, false);
        ListView listView = (ListView)v.findViewById(R.id.pantry_products_list);
        bundle = getArguments();

        db = new DatabaseHelper(getContext());
        globalApp = (Global)v.getContext().getApplicationContext();
        currentUser = globalApp.getCurrentUser();

        //group_id is passed from Lists (as a bundle)
        products = db.getProducts(globalApp.getCurrentGroupId(), "pantry");
        //link products to template list
        adapter = new adapterProductList(getContext(), products, "");
        listView.setAdapter(adapter);


        return v;
    }

}