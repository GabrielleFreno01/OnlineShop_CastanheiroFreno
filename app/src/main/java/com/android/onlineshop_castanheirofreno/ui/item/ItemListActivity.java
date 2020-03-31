package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.ItemsAdapter;
import com.android.onlineshop_castanheirofreno.adapter.OrdersAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryViewModel;
import com.android.onlineshop_castanheirofreno.ui.orders.OrderListViewModel;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends BaseActivity {

    public static final String PREFS_ITEM = "item";



    ItemEntity item;

    TextView cat_name ;

    private List<ItemEntity> items;
    private ItemsAdapter adapter;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_item_list, frameLayout);

        setTitle("Items List");
        navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.itemsRecyclerView);


        // use a grid layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration verticalDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                GridLayoutManager.VERTICAL);
        DividerItemDecoration horizontalDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                GridLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(verticalDividerItemDecoration);
        recyclerView.addItemDecoration(horizontalDividerItemDecoration);

        Intent intent = getIntent();
        long categoryId = intent.getLongExtra("categoryId", 0L);
        String name = intent.getStringExtra("categoryName");
        cat_name = findViewById(R.id.cat_name_tv);
        cat_name.setText(name);

        long idCat = intent.getLongExtra( "categoryId", 0L);
        items = new ArrayList<>();

        adapter = new ItemsAdapter( this, items , new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //get the details
                Intent intent = new Intent(ItemListActivity.this, ItemDescriptionActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION
                );
                intent.putExtra("itemId", items.get(position).getIdItem());
                intent.putExtra("idCategory", idCat);
                final SharedPreferences.Editor editor = getSharedPreferences(PREFS_ITEM, 0).edit();
                editor.putLong(PREFS_ITEM, items.get(position).getIdItem());
                editor.putLong("idCategory", idCat);
                editor.apply();
                startActivity(intent);
            }


            //Delete an order
            @Override
            public void onItemLongClick(View v, int position) {
                onItemClick(v, position);
            }
        });

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), 0L, categoryId);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItemsByCategory().observe(this, itemEntity -> {
            if (itemEntity != null) {
                items = itemEntity;
                adapter.setData(items);
            }
        });

        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.floatingActionButtonAddItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeAddNewItem(v);
            }
        });

    }
    public void seeAddNewItem (View view){
        Intent intent = new Intent(ItemListActivity.this, AddItemActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NO_ANIMATION
        );
        startActivity(intent);
    }

}
