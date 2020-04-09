package com.android.onlineshop_castanheirofreno.viewmodel.item;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.repository.CategoryRepository;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private Application application;

    private ItemRepository repository;

    private CategoryRepository catRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ItemEntity> observableItem;
    private final MediatorLiveData<List<ItemEntity>> observableItems;
    private final MediatorLiveData<List<CategoryEntity>> observableCategories;
    private final MediatorLiveData<List<ItemEntity>> observableNewItems;


    public ItemViewModel(@NonNull Application application,
                         final long idItem, final long categoryId, ItemRepository itemRepository, CategoryRepository catRepository) {
        super(application);

        this.application = application;

        repository = itemRepository;
        this.catRepository = catRepository;

        observableItem = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItem.setValue(null);

        observableCategories = new MediatorLiveData<>();
        observableCategories.setValue(null);

        observableItems = new MediatorLiveData<>();
        observableItems.setValue(null);

        observableNewItems = new MediatorLiveData<>();
        observableNewItems.setValue(null);

        /*LiveData<ItemEntity> item = repository.getItem(idItem, application);
        LiveData<List<ItemEntity>> items = repository.getItemsByCategory(categoryId, application);
        LiveData<List<CategoryEntity>> listCategory = catRepository.getCategories(application);
        LiveData<List<ItemEntity>> listNewItems = repository.getNewItems(application);*/

        // observe the changes of the account entity from the database and forward them
       /* observableItem.addSource(item, observableItem::setValue);
        observableCategories.addSource(listCategory, observableCategories::setValue);
        observableItems.addSource(items, observableItems::setValue);
        observableNewItems.addSource(listNewItems, observableNewItems::setValue);*/
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final long itemId;

        private final long categoryId;

        private final ItemRepository repository;

        private final CategoryRepository catRepository;

        public Factory(@NonNull Application application, long itemId, long categoryId) {
            this.application = application;
            this.itemId = itemId;
            this.categoryId = categoryId;
            repository = ((BaseApp) application).getItemRepository();
            catRepository = ((BaseApp) application).getCategoryRepository();
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, itemId, categoryId, repository, catRepository);
        }
    }

    public LiveData<ItemEntity> getItem() {
        return observableItem;
    }

    public LiveData<List<ItemEntity>> getItemsByCategory() {
        return observableItems;
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return observableCategories;
    }

    public LiveData<List<ItemEntity>> getNewItems() {
        return observableNewItems;
    }


    /*public void deleteItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.delete(item, callback, application);

    }

    public void createItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.insert(item, callback, application);
    }

    public void updateItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.update(item, callback, application);
    }*/
}