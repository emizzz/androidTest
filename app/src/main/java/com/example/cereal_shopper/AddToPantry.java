package com.example.cereal_shopper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/*
 *this activity can either create a product and add it to the pantry or modify/confirm an item into the pantry that was int he shopping list
 * if autocompile_fields is passed: this activity precompile the form with the product's info (the product's id is passed with the extras)
 * else: the form is initialized empty
 * */

public class AddToPantry extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText nameView;
    Global globalApp;
    private EditText weightView;
    private EditText expiryView;
    private EditText priceView;
    private EditText notesView;
    private Spinner categoriesView;
    private ArrayAdapter categAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DbProduct currentProduct;
    private int selected_category_id;
    private NumberPicker quantity;
    private long expiry = -1;
    private List<DbCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_pantry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(getApplicationContext());
        globalApp = (Global)getApplicationContext().getApplicationContext();

        //add fields
        nameView = findViewById(R.id.add_to_pantry_name);
        categoriesView = findViewById(R.id.add_to_pantry_category);
        NumberPicker quantityView = findViewById(R.id.add_to_pantry_quantity);
        weightView = findViewById(R.id.add_to_pantry_weight);
        weightView.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"));
        priceView = findViewById(R.id.add_to_pantry_price);
        priceView.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"));
        expiryView = findViewById(R.id.add_to_pantry_expiry);
        notesView = findViewById(R.id.add_to_pantry_notes);
        expiryView.setFocusable(false);

        //autocomplete categories
        categories = db.getCategories();
        List<String> categories_name = new ArrayList<>();
        for(DbCategory category : categories){
            categories_name.add(category.getName());
        }
        categAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories_name);
        categAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesView.setAdapter(categAdapter);
        categoriesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_category_id = categories.get(i).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected_category_id = categories.get(0).getId();
            }
        });

        //quantity picker
        quantity = findViewById(R.id.add_to_pantry_quantity);
        quantity.setMinValue(1);
        quantity.setMaxValue(50);

        //---------------------------------------set precompiled fields---------------------------------------
        final Bundle extras = getIntent().getExtras();

        if (extras != null && extras.getInt("autocompile_fields", -1) != -1) {

            //if the user wants to modify the product from the recapItem activity
            /*if(extras.getString("recap_item") != null){
                setTitle(extras.getString("recap_item"));
            }*/

            int productId = extras.getInt("autocompile_fields");
            currentProduct = db.getProduct(productId);

            setFields(currentProduct);

        }


        if (extras != null) {
            if(extras.getString("custom_title") != null){
                setTitle(extras.getString("custom_title"));
            }
            else{
                setTitle(currentProduct.getName());
            }
        }

        //date picker
        expiryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddToPantry.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Calendar calendar = new GregorianCalendar(year, month, day);
                expiry = calendar.getTimeInMillis()/1000;

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                expiryView.setText(String.valueOf(dateFormat.format(calendar.getTimeInMillis())));
            }
        };

        /*if (extras != null)
            getSupportActionBar().setTitle(currentProduct.getName());
        else
            getSupportActionBar().setTitle(R.string.nuovoprodotto);*/


        toolbar.setNavigationIcon(R.drawable.left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();          }
        });

        FloatingActionButton submitFab = findViewById(R.id.add_to_pantry_btn_submit);
        submitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameView.getText().toString();

                if(!name.equals("")){
                    try {

                        double weight = -1;
                        double price = -1;

                        if(!weightView.getText().toString().equals("")){
                            weight = Double.parseDouble(weightView.getText().toString().replace(',', '.'));
                        }
                        if(!priceView.getText().toString().equals("")){
                            price = Double.parseDouble(priceView.getText().toString().replace(',', '.'));
                        }



                        String notes = notesView.getText().toString();

                        DbProduct new_product = new DbProduct("pantry", globalApp.getCurrentGroupId(), 0, name, selected_category_id, quantity.getValue(), weight, price, expiry, notes);

                        //if the user wants to modify the product from the recapItem activity
                        if(extras != null && extras.getString("recap_item") != null){
                            if(extras.getInt("tab_index") == 0){ new_product.setType("shopping_list");}
                            else if(extras.getInt("tab_index") == 1){ new_product.setType("pantry");}
                        }

                        //if the product exists, take its id, if it is a new product, autoincrement the id
                        int tabIndex = 0;
                        if(currentProduct == null){
                            db.createProduct(new_product);
                            tabIndex = 1;
                        }
                        else{
                            new_product.setId(currentProduct.getId());
                            db.updateProduct(new_product);
                        }

                        //adds the price of the brought item to the user balance
                        if(new_product.getPrice()!=-1){
                            double newB= globalApp.getCurrentUser().getBalance()+ new_product.getPrice();
                            globalApp.getCurrentUser().setBalance(newB);
                            globalApp.db.updateUser(globalApp.getCurrentUser());
                        }

                        Intent intent = new Intent(getApplicationContext(), Lists.class);
                        intent.putExtra( "tab_index", tabIndex );
                        startActivity(intent);

                    }
                    catch(Exception e){
                        Toast.makeText(AddToPantry.this, "Errore interno 0",
                                Toast.LENGTH_SHORT).show();
                        Log.d("AddToPantry", e.getMessage());
                    }
                }
                else{
                    Toast.makeText(AddToPantry.this, getString(R.string.scegli_nome_prodotto),
                            Toast.LENGTH_SHORT).show();
                }



            }
        });


        FloatingActionButton cameraFab = findViewById(R.id.add_to_pantry_btn_barcode);
        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddToPantry.this, getString(R.string.funz_barrcode),
                        Toast.LENGTH_SHORT).show();

                DbProduct testProduct = globalApp.getBarcodeTestProduct();
                //autocomplete the fields with the data in testProduct (simulate the barcode scan)
                setFields(testProduct);
            }
        });

        //if the user wants to modify the product from the recapItem activity
        if(extras != null && extras.getString("recap_item") != null){
            cameraFab.hide();
        }

    }

    void setFields(DbProduct product){
        //set name
        nameView.setText( product.getName() );


        //set categories
        int favoriteCatId = product.getCategoryId();
        for(DbCategory category : categories){
            if(category.getId() == favoriteCatId){
                categoriesView.setSelection(categAdapter.getPosition(category.getName()));
            }
        }

        //set quantity
        quantity.setValue(product.getQuantity());

        //set weight
        if(product.getWeight() != -1){
            weightView.setText(String.valueOf(product.getWeight()));
        }

        //set price
        if(product.getPrice() != -1){
            priceView.setText(String.valueOf(product.getPrice()));
        }

        //set expiry
        if(product.getExpiry() != -1){
            Timestamp ts = new Timestamp(product.getExpiry());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            expiry = ts.getTime();
            Date date=new Date(ts.getTime()*1000);
            expiryView.setText(String.valueOf(dateFormat.format(date)));
        }

        //set notes
        notesView.setText(product.getNotes());

    }

}