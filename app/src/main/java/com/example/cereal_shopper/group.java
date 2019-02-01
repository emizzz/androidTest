package com.example.cereal_shopper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        NumberPicker np = findViewById(R.id.numberPicker);

        np.setMinValue(0);
        np.setMaxValue(50);
    }
}
