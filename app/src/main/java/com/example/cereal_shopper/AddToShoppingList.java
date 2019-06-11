package com.example.cereal_shopper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
*this activity can either create a product and add it to the shopping list or confirm a favorite product
**/
public class AddToShoppingList extends AppCompatActivity {
    DatabaseHelper db;
    Global globalApp;
    DbUser currentUser;
    Button addButton;
    Toolbar toolbar;
    int selected_category_id = -1;
    EditText nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_spesa);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nameView = findViewById(R.id.add_list_product_name);
        db = new DatabaseHelper(getApplicationContext());
        globalApp = (Global)getApplicationContext().getApplicationContext();
        currentUser = globalApp.getCurrentUser();
        setTitle("Aggiungi alla lista");

        //autocomplete categories
        final List<DbCategory> categories = db.getCategories();
        List<String> categories_name = new ArrayList<>();
        for(DbCategory category : categories){
            categories_name.add(category.getName());
        }
        AppCompatSpinner categoriesView = (AppCompatSpinner) findViewById(R.id.list_categories);
        ArrayAdapter categAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories_name);
        categAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesView.setAdapter(categAdapter);
        categoriesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_category_id = categories.get(i).getId();
                //Log.d("selectedcategory: ",  Integer.toString(selected_category_id) + " " + categories.get(i).getName());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected_category_id = categories.get(0).getId();
            }
        });

        //quantity picker
        final NumberPicker quantity = findViewById(R.id.numberpicker);
        quantity.setMinValue(1);
        quantity.setMaxValue(50);


        /*------------------------------------------------------------------------------
        * when the user clicks on a favorite item, the app precompiles the form
        * ------------------------------------------------------------------------------*/
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getInt("autocompile_fields", -1) != -1) {
            int productId = extras.getInt("autocompile_fields");

            DbProduct favoriteSelectedProduct = db.getProduct(productId);

            //set name
            nameView.setText(favoriteSelectedProduct.getName());

            //set categories
            int favoriteCatId = favoriteSelectedProduct.getCategoryId();
            for(DbCategory category : categories){
                if(category.getId() == favoriteCatId){
                    categoriesView.setSelection(categAdapter.getPosition(category.getName()));
                }
            }
            quantity.setValue(favoriteSelectedProduct.getQuantity());

        }



        //submit data
        addButton= (Button) findViewById(R.id.add_list_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameView.getText().toString();

                if(!name.equals("")){

                    try{
                        EditText notestView = findViewById(R.id.add_list_product_notes);
                        int quantity_val = quantity.getValue();
                        String notes = notestView.getText().toString();
                        DbProduct new_product = new DbProduct("shopping_list", globalApp.getCurrentGroupId(), 0, name, selected_category_id, quantity_val, -1, -1, -1, notes);
                        db.createProduct(new_product);
                        startActivity(new Intent(getApplicationContext(), Lists.class));
                    }
                    catch(Exception e){
                        Toast.makeText(AddToShoppingList.this, "Errore interno",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddToShoppingList.this, R.string.scegli_nome_prodotto,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        //favorites products
        ListView favoritesView = findViewById(R.id.add_list_product_favorites);
        List<DbProduct> favoritesProducts = db.getFavoritesProducts(globalApp.getCurrentGroupId());
        adapterProductList favoritesAdapter = new adapterProductList(getApplicationContext(), favoritesProducts, "click_in_favorites");
        favoritesView.setAdapter(favoritesAdapter);



    }
}
