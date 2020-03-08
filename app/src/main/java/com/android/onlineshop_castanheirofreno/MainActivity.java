package com.android.onlineshop_castanheirofreno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;

import com.android.onlineshop_castanheirofreno.adapter.NewItemsAdapter;
import com.android.onlineshop_castanheirofreno.model.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private Button cat;

    ViewPager viewPager;
    NewItemsAdapter newItemsAdapter;
    List<ItemModel> models;

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

        //Adding products to the List containing the new products
        models = new ArrayList<>();
        models.add(new ItemModel(R.drawable.acer_aspire_5_599, "Acer Aspire 5", "CHF 599.00"));
        models.add(new ItemModel(R.drawable.apple_imac_1499, "Apple iMac", "CHF 1499.00"));
        models.add(new ItemModel(R.drawable.acer_aspire_5_599, "Acer Aspire 5", "CHF 599.00"));
        models.add(new ItemModel(R.drawable.apple_imac_1499, "Apple iMac", "CHF 1499.00"));

        newItemsAdapter = new NewItemsAdapter(models,this);

        viewPager = findViewById(R.id.newProducts_viewPager);
        viewPager.setAdapter(newItemsAdapter);
        viewPager.setPadding(150,0,150,0);

    }



}

