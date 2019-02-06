package com.example.cereal_shopper;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class addItem extends AppCompatActivity {
    int NUMBER_OF_VALUES = 21; //num of values in the picker
    int PICKER_RANGE = 50;
    FloatingActionButton addButton;
    String oldName;
    String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        final NumberPicker quantity = findViewById(R.id.numberpicker);
        final NumberPicker weight = findViewById(R.id.pickerweight);


        quantity.setMinValue(0);
        quantity.setMaxValue(50);


//make to piker go from 50 t 50
        String[] displayedValues = new String[NUMBER_OF_VALUES];
        for (int i = 0; i < NUMBER_OF_VALUES; i++)
            displayedValues[i] = String.valueOf(PICKER_RANGE * (i));
        weight.setMinValue(0);
        weight.setMaxValue(displayedValues.length - 1);
        weight.setDisplayedValues(displayedValues);
        if (getIntent().getStringExtra("NAME")!=null) {
            realName=getIntent().getStringExtra("NAME");
            oldName=realName;
            TextView name = findViewById(R.id.edittextname);
            name.setText(realName);
        }

        addButton= (FloatingActionButton) findViewById(R.id.checkbutton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText product = (EditText) findViewById(R.id.edittextname);
                final String nameString = product.getText().toString();

                EditText category = (EditText) findViewById(R.id.edittextcategory);
                String categoryString = category.getText().toString();

                int quantityInt = quantity.getValue();
                int weightInt = quantity.getValue();

                EditText price = (EditText) findViewById(R.id.price);
                String priceString = category.getText().toString();

                EditText expiration = (EditText) findViewById(R.id.expiration);
                String expirationString = category.getText().toString();

                EditText notes = (EditText) findViewById(R.id.editnotes);
                String notesString = notes.getText().toString();

                Intent data = new Intent ();
                data.putExtra("NAME",nameString);
                data.putExtra("CATEGORY",categoryString);
                data.putExtra("QUANTITY",quantityInt);
                data.putExtra("WEIGHT",weightInt);
                data.putExtra("PRICE",priceString);
                data.putExtra("EXPIRATION",quantityInt);
                data.putExtra("NOTE",notesString);
                setResult(Activity.RESULT_OK,data);
                finish();

            }
        });
    }
}
