package com.android.onlineshop_castanheirofreno.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.CategoryAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.ui.item.ItemListActivity;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;
import com.android.onlineshop_castanheirofreno.viewmodel.category.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity {

    private static final String TAG = "CategoryActivity";

    private List<CategoryEntity> categoryList;
    private CategoryAdapter adapter;
    private CategoryViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_category, frameLayout);

        setTitle("Categories");
        navigationView.setCheckedItem(R.id.nav_category);

        RecyclerView recyclerView = findViewById(R.id.categoryRecyclerView);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        categoryList = new ArrayList<>();

        adapter = new CategoryAdapter(this, categoryList, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //get the details
                Intent intent = new Intent(CategoryActivity.this, ItemListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("categoryId", categoryList.get(position).getIdCategory());
                intent.putExtra("categoryName", categoryList.get(position).getName());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View v, int position) {
                onItemClick(v, position);
            }
        });

        CategoryViewModel.Factory factory = new CategoryViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(CategoryViewModel.class);
        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                categoryList = categories;
                adapter.setData(categoryList);
            }
        });

        recyclerView.setAdapter(adapter);

    }

}
