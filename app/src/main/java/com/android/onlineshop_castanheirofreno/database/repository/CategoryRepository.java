package com.android.onlineshop_castanheirofreno.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.CategoryItemsLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.CategoryListLiveData;
import com.android.onlineshop_castanheirofreno.database.pojo.CategoryWithItems;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CategoryRepository {

    private static CategoryRepository instance;

    public CategoryRepository() {
    }

    public static CategoryRepository getInstance() {
        if (instance == null) {
            synchronized (CategoryRepository.class) {
                if (instance == null) {
                    instance = new CategoryRepository();
                }
            }
        }
        return instance;
    }



    public LiveData<List<CategoryEntity>> getCategories(Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories");
        return new CategoryListLiveData(reference);

    }

    public LiveData<CategoryWithItems> getCategoryWithItems(Application application, String idCategory) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(idCategory);
        return new CategoryItemsLiveData(reference);

    }

}
