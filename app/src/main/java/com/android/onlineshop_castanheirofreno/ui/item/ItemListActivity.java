package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.ItemsAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CategoryWithItems;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends BaseActivity {

    public static final String PREFS_ITEM = "item";


    ItemEntity item;

    TextView cat_name;

    private List<ItemEntity> items;
    private CategoryWithItems categoryWithItems;
    private ItemsAdapter adapter;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_item_list, frameLayout);

        setTitle("Items List");
        navigationView.setCheckedItem(R.id.nav_category);

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
        String categoryId = intent.getStringExtra("categoryId");
        String name = intent.getStringExtra("categoryName");
        cat_name = findViewById(R.id.cat_name_tv);

        items = new ArrayList<>();

        adapter = new ItemsAdapter(this, items, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //get the details
                Intent intent = new Intent(ItemListActivity.this, ItemDescriptionActivity.class);
                intent.putExtra("itemId", items.get(position).getIdItem());
                intent.putExtra("idCategory", items.get(position).getIdCategory());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                onItemClick(v, position);
            }
        });

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), "", categoryId);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getCategoryWithItems().observe(this, categoryWithItemsList ->  {
            if (categoryWithItemsList != null) {
                categoryWithItems = categoryWithItemsList;
                items = categoryWithItemsList.items;
                adapter.setData(items);
                cat_name.setText(categoryWithItems.category.getName());
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

    public void seeAddNewItem(View view) {
        Intent intent = new Intent(ItemListActivity.this, AddItemActivity.class);
        startActivity(intent);

    }

}
