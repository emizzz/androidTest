package com.example.cereal_shopper;

import android.app.Activity;
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

public class recapItem extends AppCompatActivity {

    TextView tvName;
    TextView tvCategory;
    TextView tvQuantity;
    TextView tvWeight;
    TextView tvPrice;
    TextView tvExpiration;
    TextView tvNote;

    String oldname="";
    String newname="";
    String newcategory="";
    int newquantity=0;
    int newweight=0;
    String newprice="";
    String newexpiration="";
    String newnote="";


    FloatingActionButton fbtn;
    Toolbar toolbar;


    boolean editable=false;
    boolean isList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_item);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        isList=getIntent().getBooleanExtra("ISLISTASPESA",false);
        oldname=getIntent().getStringExtra("OLDNAME");
        newname=oldname;
     //   newcategory=getIntent().getStringExtra("CATEGORY");
        newquantity=getIntent().getIntExtra("QUANTITY",0);
        newweight=getIntent().getIntExtra("WEIGHT",0);
        newprice=getIntent().getStringExtra("PRICE");
        newexpiration=getIntent().getStringExtra("EXPIRATION");
        newnote=getIntent().getStringExtra("CATEGORY");

        recap();

        if(isList) {
            LinearLayout w = (LinearLayout)findViewById(R.id.layrecwheight) ;
            w.setVisibility(LinearLayout.GONE);
            LinearLayout p = (LinearLayout)findViewById(R.id.layrecPrice) ;
            p.setVisibility(LinearLayout.GONE);
            LinearLayout e = (LinearLayout)findViewById(R.id.layrecExpiration) ;
            e.setVisibility(LinearLayout.GONE);
        }


        fbtn= (FloatingActionButton) findViewById(R.id.updateRecapItem);

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editable) fbtn.setImageResource(R.drawable.check48);
                else {
                    //fbtn.setImageResource(android.R.drawable.ic_menu_edit);
                    EditText product = (EditText) findViewById(R.id.receditName);
                    final String nameString = product.getText().toString();

                    EditText category = (EditText) findViewById(R.id.receditCategory);
                    String categoryString = category.getText().toString();

                    EditText quantity = (EditText) findViewById(R.id.receditQuantity);
                    int quantityInt =Integer.parseInt( quantity.getText().toString());

                    EditText weight = (EditText) findViewById(R.id.receditweight);
                    int weightInt =Integer.parseInt( weight.getText().toString());

                    EditText price = (EditText) findViewById(R.id.receditprice);
                    String priceString = price.getText().toString();

                    EditText expiration = (EditText) findViewById(R.id.receditExpiration);
                    String expirationString = expiration.getText().toString();

                    EditText notes = (EditText) findViewById(R.id.receditnotes);
                    String notesString = notes.getText().toString();


                    Intent data = new Intent ();
                    data.putExtra("ISLIST", isList);
                    data.putExtra("OLDNAME",oldname);
                    data.putExtra("NAME",nameString);
           //         data.putExtra("CATEGORY",categoryString);
                    data.putExtra("QUANTITY",quantityInt);
                    data.putExtra("WEIGHT",weightInt);
                    data.putExtra("PRICE",priceString);
                    data.putExtra("EXPIRATION",expirationString);
                    data.putExtra("NOTE",notesString);
                    setResult(Activity.RESULT_OK,data);
                    finish();

                }
                editable=!editable;
                recap();

            }
        });



    }


    public void recap(){
        InputMethodManager imm = (InputMethodManager)getSystemService(
                getApplicationContext().INPUT_METHOD_SERVICE);

        tvName=(TextView)  findViewById(R.id.receditName);
        tvName.setText(newname);
        tvName.setFocusable(editable);
        tvName.setFocusableInTouchMode(editable);
        imm.hideSoftInputFromWindow(tvName.getWindowToken(), 0);

/*
        tvCategory=(TextView)  findViewById(R.id.receditCategory);
        tvCategory.setText(newcategory);
        tvCategory.setFocusable(editable);
        tvCategory.setFocusableInTouchMode(editable);
        imm.hideSoftInputFromWindow(tvCategory.getWindowToken(), 0);

*/

        tvQuantity=(TextView)  findViewById(R.id.receditQuantity);
        tvQuantity.setText(Integer.toString(newquantity));
        tvQuantity.setFocusable(editable);
        tvQuantity.setFocusableInTouchMode(editable);
        imm.hideSoftInputFromWindow(tvQuantity.getWindowToken(), 0);

        tvWeight=(TextView)  findViewById(R.id.receditweight);
        tvWeight.setText(Integer.toString(newweight));
        tvWeight.setFocusable(editable);
        tvWeight.setFocusableInTouchMode(editable);
        imm.hideSoftInputFromWindow(tvWeight.getWindowToken(), 0);


        tvPrice=(TextView)  findViewById(R.id.receditprice);
        tvPrice.setText(newprice);
        tvPrice.setFocusable(editable);
        tvPrice.setFocusableInTouchMode(editable);
        imm.hideSoftInputFromWindow(tvPrice.getWindowToken(), 0);


        tvExpiration=(TextView)  findViewById(R.id.receditExpiration);
        tvExpiration.setText(newexpiration);
        tvExpiration.setFocusable(editable);
        tvExpiration.setFocusableInTouchMode(editable);
        imm.hideSoftInputFromWindow(tvExpiration.getWindowToken(), 0);


        tvNote=(TextView)  findViewById(R.id.receditnotes);
        tvNote.setText(newnote);
        tvNote.setFocusable(editable);
        tvNote.setFocusableInTouchMode(editable);
        imm.hideSoftInputFromWindow(tvNote.getWindowToken(), 0);

    }
}