package com.android.onlineshop_castanheirofreno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.onlineshop_castanheirofreno.adapter.NewItemsAdapter;
import com.android.onlineshop_castanheirofreno.model.ItemModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ImageButton categoryButton;
    ImageButton cartButton;
    ImageButton ordersButton;

    ViewPager viewPager;
    NewItemsAdapter newItemsAdapter;
    List<ItemModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding all button in the view
        this.categoryButton = findViewById(R.id.btn_category);
        this.cartButton = findViewById(R.id.btn_cart);
        this.ordersButton = findViewById(R.id.btn_orders);


        //Setting the Listeners for the buttons
        categoryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);

            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
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
