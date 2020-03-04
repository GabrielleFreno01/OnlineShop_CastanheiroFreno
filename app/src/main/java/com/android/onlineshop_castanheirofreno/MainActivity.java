package com.android.onlineshop_castanheirofreno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void seeCategories (View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void seeDescriptions (View view){
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        startActivity(intent);
    }




}
