package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ItemListActivity extends BaseActivity {

    public static final String PREFS_ITEM = "item";

    ItemEntity item;

    GridView gridView;
    TextView cat_name ;

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
            R.drawable.acer_aspire_5_599, R.drawable.apple_imac_1499};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_item_list, frameLayout);

        setTitle("ItemList");
        navigationView.setCheckedItem(position);

        gridView = findViewById(R.id.items_gv);

        ItemListAdapter adapter = new ItemListAdapter(ItemListActivity.this, imagesList, itemsList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeProductDescription(view);
                //TODO remplacer par idItem
               SharedPreferences.Editor editor = getSharedPreferences(PREFS_ITEM, 0).edit();
                editor.putLong(PREFS_ITEM, 1);
                editor.apply();
            }
        });

        Intent intent =  getIntent();
        String name = intent.getStringExtra(CategoryActivity.EXTRA_MESSSAGE);


        //Replace by a function that gets the order with the idOrder
        CategoryViewModel cat_v_model = new CategoryViewModel(name, null);

        cat_name = findViewById(R.id.cat_name_tv);
        cat_name.setText(cat_v_model.getCategoryName());

        FloatingActionButton fab = findViewById(R.id.floatingActionButtonAddItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeAddNewItem(v);
            }
        });

    }
    public void seeProductDescription (View view){
        Intent intent = new Intent(this, ItemDescriptionActivity.class);
        startActivity(intent);
    }
    public void seeAddNewItem (View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

}
