package com.example.cereal_shopper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingList extends Fragment{
    private DatabaseHelper db;
    private FloatingActionButton fab;
    private Global globalApp;
    private DbUser currentUser;
    private ArrayList<DbProduct> products;
    private adapterProductList adapter;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista_spesa, container, false);
        ListView listView = (ListView)v.findViewById(R.id.shopping_products_list);
        bundle = getArguments();

        db = new DatabaseHelper(getContext());
        globalApp = (Global)v.getContext().getApplicationContext();
        currentUser = globalApp.getCurrentUser();

        //group_id is passed from Lists (as a bundle)
        products = db.getProducts(globalApp.getCurrentGroupId(), "shopping_list");
        //link products to template list
        adapter = new adapterProductList(getContext(), products, "click_in_shopping_list");
        listView.setAdapter(adapter);


        fab = (FloatingActionButton) v.findViewById(R.id.addtospesa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddToShoppingList.class);
                //intent.putExtra("group_id", bundle.getInt("group_id"));
                getActivity().startActivity(intent);

            }
        });


        return v;
    }

}