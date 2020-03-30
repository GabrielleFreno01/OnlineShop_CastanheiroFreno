package com.android.onlineshop_castanheirofreno.ui.item;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.repository.CategoryRepository;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class ItemViewModel  extends AndroidViewModel {

    private Application application;

    private ItemRepository repository;

    private CategoryRepository catRepository ;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ItemEntity> observableItem;
    private final MediatorLiveData<List<CategoryEntity>> observableCategories;


    public ItemViewModel(@NonNull Application application,
                            final long idItem, ItemRepository itemRepository, CategoryRepository catRepository) {
        super(application);

        this.application = application;

        repository = itemRepository;
        this.catRepository= catRepository;

        observableItem= new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItem.setValue(null);

        observableCategories = new MediatorLiveData<>();
        observableCategories.setValue(null);

        LiveData<ItemEntity> item = repository.getItem(idItem, application);
        LiveData<List<CategoryEntity>> listCategory = catRepository.getCategories(application);

        // observe the changes of the account entity from the database and forward them
        observableItem.addSource(item, observableItem::setValue);
        observableCategories.addSource(listCategory, observableCategories::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final long itemId;

        private final ItemRepository repository;

        private final CategoryRepository catRepository;

        public Factory(@NonNull Application application, long itemId) {
            this.application = application;
            this.itemId = itemId;
            repository = ((BaseApp) application).getItemRepository();
            catRepository = ((BaseApp) application).getCategoryRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, itemId, repository, catRepository);
        }
    }

    public LiveData<ItemEntity> getItem() {
        return observableItem;
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return observableCategories;
    }


    public void deleteItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.delete(item, callback, application);

    }

    public void createItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.insert(item, callback, application);
    }

    public void updateItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.update(item, callback, application);
    }
}