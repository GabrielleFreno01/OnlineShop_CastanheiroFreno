package com.android.onlineshop_castanheirofreno.database.repository;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.CategoriesLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public LiveData<CategoryEntity> getCategories() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories")
                .child("name");
        return new CategoriesLiveData(reference);
    }
/*
    public LiveData<List<CategoryEntity>> getCategories(Application application) {
        LiveData<List<CategoryEntity>> category = ((BaseApp) application).getDatabase().categoryDao().getAll();
        //System.out.println(category.getValue().get(1));
        return category;
    }

    public LiveData<CategoryEntity> getCategory(final Long categoryId, Application application) {
        return ((BaseApp) application).getDatabase().categoryDao().getById(categoryId);
    }*/

}
