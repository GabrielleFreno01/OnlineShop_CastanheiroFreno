package com.android.onlineshop_castanheirofreno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.onlineshop_castanheirofreno.adapter.ItemsListAdapter;

public class ItemsListActivity extends AppCompatActivity {

    GridView gridView;

    String [][] itemsList = {{"Acer Aspire 5", "CHF 599.00"},{"Apple iMac", "CHF 1499.00"},
            {"Acer Aspire 5", "CHF 599.00"},
            {"Apple iMac", "CHF 1499.00"},{"Acer Aspire 5", "CHF 599.00"},
            {"Apple iMac", "CHF 1499.00"},{"Acer Aspire 5", "CHF 599.00"},
            {"Apple iMac", "CHF 1499.00"},{"Acer Aspire 5", "CHF 599.00"},
            {"Apple iMac", "CHF 1499.00"},{"Acer Aspire 5", "CHF 599.00"},
            {"Apple iMac", "CHF 1499.00"},{"Acer Aspire 5", "CHF 599.00"},
            {"Apple iMac", "CHF 1499.00"}};

    int[] imagesList = {R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499,R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499,
            R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499,
            R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499,
            R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499,
            R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499,
            R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_list);

        gridView = findViewById(R.id.items_gv);

        ItemsListAdapter adapter = new ItemsListAdapter(ItemsListActivity.this, imagesList, itemsList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeProductDescription(view);
            }
        });

    }
    public void seeProductDescription (View view){
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        startActivity(intent);
    }
    public void seeAddNewItem (View view){
        Intent intent = new Intent(this, AddNewItemActivity.class);
        startActivity(intent);
    }

}
