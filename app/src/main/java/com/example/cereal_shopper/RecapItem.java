package com.example.cereal_shopper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * activity with the attributes of the item
 **/
public class RecapItem extends AppCompatActivity {
    private FloatingActionButton delete_btn;
    private FloatingActionButton modify_btn;
    private Toolbar toolbar;
    private DbProduct currentProduct;
    private DatabaseHelper db;


    private TextView nameView;
    private TextView categoryView;
    private TextView quantityView;
    private TextView weightView;
    private TextView priceView;
    private TextView expiryView;
    private TextView notesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_item);

        db = new DatabaseHelper(getApplicationContext());

        nameView = findViewById(R.id.recap_name);
        categoryView = findViewById(R.id.recap_category);
        quantityView = findViewById(R.id.recap_quantity);
        weightView = findViewById(R.id.recap_weight);
        priceView = findViewById(R.id.recap_price);
        expiryView = findViewById(R.id.recap_expiry);
        notesView = findViewById(R.id.recap_notes);


        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getInt("product_id", -1) != -1) {
            int productId = -1;
            productId = extras.getInt("product_id");

            if(productId != -1 && productId != 0){
                currentProduct = db.getProduct(productId);
                compileFields(currentProduct);
            }
            else{
                Toast.makeText(RecapItem.this, getString(R.string.prodotto_non_trovato),
                        Toast.LENGTH_SHORT).show();
            }
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left);
        getSupportActionBar().setTitle(currentProduct.name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //---------------------------------------------fab buttons---------------------------------------------
        delete_btn= (FloatingActionButton) findViewById(R.id.recap_btn_delete);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View _v = v;

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.deleteProduct(currentProduct.getId());
                                Intent intent = new Intent(_v.getContext(), Lists.class);

                                Bundle extras = getIntent().getExtras();
                                if (extras != null && !extras.getString("from").equals("")) {
                                    String from = extras.getString("from");

                                    if(from.equals("click_in_pantry_list")){
                                        intent.putExtra( "tab_index", 1 );
                                    }
                                    else{
                                        intent.putExtra( "tab_index", 0 );
                                    }
                                }
                                _v.getContext().startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(getString(R.string.elimina_prodotto)).setPositiveButton(R.string.si, dialogClickListener)
                        .setNegativeButton(R.string.no, dialogClickListener).show();
            }
        });
        modify_btn= (FloatingActionButton) findViewById(R.id.recap_btn_modify);
        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddToPantry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("autocompile_fields", currentProduct.getId());
                intent.putExtra("recap_item", R.string.modifica_prodotto);

                Bundle extras = getIntent().getExtras();
                if (extras != null && !extras.getString("from").equals("")) {
                    String from = extras.getString("from");
                    if(from.equals("click_in_pantry_list")){
                        intent.putExtra( "tab_index", 1 );
                    }
                    else{
                        intent.putExtra( "tab_index", 0 );
                    }
                }

                v.getContext().startActivity(intent);
            }
        });


    }
    void compileFields(DbProduct _product){
        //get category name
        List<DbCategory> categories = db.getCategories();
        List<String> categories_name = new ArrayList<>();
        String catName = "";
        for(DbCategory category : categories){
            if(category.getId() == _product.getCategoryId()){
                catName = category.getName();
                break;
            }
        }

        nameView.setText(_product.getName());
        categoryView.setText(catName);

        if(_product.getQuantity() != -1){
            quantityView.setText(String.valueOf(_product.getQuantity()));
        }
        if(_product.getWeight() != -1){
            weightView.setText(String.valueOf(_product.getWeight()));
        }
        if(_product.getPrice() != -1){
            priceView.setText(String.valueOf(_product.getPrice()));
        }
        if(_product.getExpiry() != -1){
            Timestamp ts = new Timestamp(_product.getExpiry());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            long expiry = ts.getTime();
            Date date=new Date(ts.getTime()*1000);
            expiryView.setText(String.valueOf(dateFormat.format(date)));
        }
        notesView.setText(String.valueOf(_product.getNotes()));
    }
}