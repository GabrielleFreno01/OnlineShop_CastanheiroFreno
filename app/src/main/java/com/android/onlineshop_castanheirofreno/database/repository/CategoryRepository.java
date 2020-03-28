package com.android.onlineshop_castanheirofreno.database.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;

import java.util.List;

public class CategoryRepository  {

    private static CategoryRepository instance;

    public CategoryRepository() {}

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
        return ((BaseApp) application).getDatabase().categoryDao().getAll();
    }

    public LiveData<CategoryEntity> getCategory(final Long categoryId, Application application) {
        return ((BaseApp) application).getDatabase().categoryDao().getById(categoryId);
    }

}
