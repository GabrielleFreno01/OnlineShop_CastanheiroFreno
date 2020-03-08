package com.android.onlineshop_castanheirofreno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cat = findViewById(R.id.btn_category);

        cat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);

            }
        });
    }




    public void seeDescriptions (View view){
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        startActivity(intent);
    }




}
