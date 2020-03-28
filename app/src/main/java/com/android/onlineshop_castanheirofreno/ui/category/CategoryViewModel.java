package com.android.onlineshop_castanheirofreno.ui.category;

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
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class CategoryViewModel extends ViewModel {


    private String categoryName;
    private String tag;

    public CategoryViewModel(String categoryName, String tag) {

        this.categoryName = categoryName;
        this.tag = tag ;

    }

    public String getCategoryName(){
        return categoryName;
    }

    public String getTag(){
        return tag;
    }
}

/*public class CategoryViewModel extends AndroidViewModel {

    private Application application;

    private CategoryRepository repository;

    private final MediatorLiveData<CategoryEntity> observableCategory;

    public CategoryViewModel(@NonNull Application application,
                            final Long accountId, CategoryRepository categoryRepository) {
        super(application);

        this.application = application;

        repository = categoryRepository;

        observableCategory = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCategory.setValue(null);

        LiveData<CategoryEntity> account = repository.getCategory(accountId, application);

        // observe the changes of the account entity from the database and forward them
        observableCategory.addSource(account, observableCategory::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long categoryId;

        private final CategoryRepository repository;

        public Factory(@NonNull Application application, Long categoryId) {
            this.application = application;
            this.categoryId = categoryId;
            repository = ((BaseApp) application).getCategoryRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CategoryViewModel(application, categoryId, repository);
        }
    }

    public LiveData<CategoryEntity> getAccount() {
        return observableCategory;
    }



}*/