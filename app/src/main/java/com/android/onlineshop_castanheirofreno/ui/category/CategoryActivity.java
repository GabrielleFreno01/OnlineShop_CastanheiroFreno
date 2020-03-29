package com.android.onlineshop_castanheirofreno.ui.category;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.OrdersAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.item.ItemListActivity;
import com.android.onlineshop_castanheirofreno.ui.orders.OrderDetailActivity;
import com.android.onlineshop_castanheirofreno.ui.orders.OrderListViewModel;
import com.android.onlineshop_castanheirofreno.ui.orders.OrdersActivity;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity {



    private static final String TAG = "CategoriesActivity";

    private List<CategoryEntity> categories;
    private OrderListViewModel viewModel;

    private List<CategoryViewModel> categoryList = new ArrayList<>();
    public static final String EXTRA_MESSSAGE ="ch.hevs.aislab.demo.ui.categories;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_category, frameLayout);

        setTitle("Categories");
        navigationView.setCheckedItem(position);

        categoryList.add(new CategoryViewModel("Audio","audio"));
        categoryList.add(new CategoryViewModel("Camera", "camera"));
        categoryList.add(new CategoryViewModel("Computer", "computer"));
        categoryList.add(new CategoryViewModel("Gaming","gaming"));
        categoryList.add(new CategoryViewModel("Smartphone","smartphone"));
        categoryList.add(new CategoryViewModel("TV","tv"));


        //get listView
        ListView categoryListView = findViewById(R.id.category_list);
        categoryListView.setAdapter(new CategoryListAdapter(this, categoryList));

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeItemsList(view, categoryList.get(position).getCategoryName());
            }
        });
    }

    public void seeItemsList (View view, String categoryName){
        Intent intent = new Intent(this, ItemListActivity.class);
        TextView name = (TextView) view.findViewById(R.id.category_name);
        String cat_name = name.getText().toString();
        intent.putExtra(EXTRA_MESSSAGE, cat_name);

        startActivity(intent);
    }
}
/*public class CategoryActivity extends BaseActivity {

    private List<CategoryEntity> categoryList = new ArrayList<>();

    CategoryRepository repository;

    Application application;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_category, frameLayout);



        setTitle("Categories");
        navigationView.setCheckedItem(position);

        repository = ((BaseApp) getApplication()).getCategoryRepository();;

        //TODO récupérer la table catégorie
        LiveData<List<CategoryEntity>> category = repository.getCategories(application);




        //get listView
        ListView categoryListView = findViewById(R.id.category_list);
        categoryListView.setAdapter(new CategoryListAdapter(this, category.getValue()));

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeItemsList(view, categoryList.get(position).getName());
            }
        });
    }

    public void seeItemsList (View view, String categoryName){
        Intent intent = new Intent(this, ItemListActivity.class);
        TextView name = (TextView) view.findViewById(R.id.category_name);
        long id_cat = name.getId();
        intent.putExtra("id_category", id_cat);

        startActivity(intent);
    }
}
*/