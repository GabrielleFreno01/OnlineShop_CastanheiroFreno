package com.android.onlineshop_castanheirofreno;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.onlineshop_castanheirofreno.adapter.CategoryListAdapter;
import com.android.onlineshop_castanheirofreno.model.CategoryListModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        //Category List
        List<CategoryListModel> categoryList = new ArrayList<>();

        categoryList.add(new CategoryListModel("Audio","audio"));
        categoryList.add(new CategoryListModel("Camera", "camera"));
        categoryList.add(new CategoryListModel("Computer", "computer"));
        categoryList.add(new CategoryListModel("Gaming","gaming"));
        categoryList.add(new CategoryListModel("Smartphone","smartphone"));
        categoryList.add(new CategoryListModel("TV","tv"));


        //get la listView
        ListView categoryListView = findViewById(R.id.category_list);
        categoryListView.setAdapter(new CategoryListAdapter(this, categoryList));
    }
}
