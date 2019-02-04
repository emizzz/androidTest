package com.example.cereal_shopper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class addToSpesa extends AppCompatActivity {

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_spesa);

        final NumberPicker quantity = findViewById(R.id.numberpicker);

        quantity.setMinValue(0);
        quantity.setMaxValue(50);

        addButton= (Button) findViewById(R.id.addItemButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText product = (EditText) findViewById(R.id.EditTextName);
                final String nameString = product.getText().toString();

                EditText category = (EditText) findViewById(R.id.EditTextCategory);
                String categoryString = category.getText().toString();

                int quantityInt = quantity.getValue();

                EditText notes = (EditText) findViewById(R.id.EditNotes);
                String notesString = notes.getText().toString();

                Intent data = new Intent ();
                data.putExtra("NAME",nameString);
                data.putExtra("CATEGORY",categoryString);
                data.putExtra("QUANTITY",quantityInt);
                data.putExtra("NOTE",notesString);
                setResult(Activity.RESULT_OK,data);
                finish();
            }
        });



    }
}
