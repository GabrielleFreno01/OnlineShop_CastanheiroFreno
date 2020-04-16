package com.android.onlineshop_castanheirofreno.viewmodel.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private Application application;

    private CategoryRepository repository;

    private final MediatorLiveData<List<CategoryEntity>> observableCategories;

    public CategoryViewModel(@NonNull Application application, CategoryRepository categoryRepository) {
        super(application);

        this.application = application;

        repository = categoryRepository;

        observableCategories = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCategories.setValue(null);

        LiveData<List<CategoryEntity>> categories = repository.getCategories(application);

        // observe the changes of the account entity from the database and forward them
        observableCategories.addSource(categories, observableCategories::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CategoryRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getCategoryRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CategoryViewModel(application, repository);
        }
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return observableCategories;
    }


}