package com.android.onlineshop_castanheirofreno.ui.item;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class ItemViewModel  extends AndroidViewModel {

    private Application application;

    private ItemRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ItemEntity> observableItem;

    public ItemViewModel(@NonNull Application application,
                            final long idItem, ItemRepository itemRepository) {
        super(application);

        this.application = application;

        repository = itemRepository;

        observableItem= new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItem.setValue(null);

        LiveData<ItemEntity> item = repository.getItem(idItem, application);

        // observe the changes of the account entity from the database and forward them
        observableItem.addSource(item, observableItem::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final long itemId;

        private final ItemRepository repository;

        public Factory(@NonNull Application application, long itemId) {
            this.application = application;
            this.itemId = itemId;
            repository = ((BaseApp) application).getItemRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, itemId, repository);
        }
    }

    public LiveData<ItemEntity> getItem() {
        return observableItem;
    }
    public LiveData<ItemEntity> getAccount() {
        return observableItem;
    }

    public void createItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.insert(item, callback, application);
    }

    public void updateItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.update(item, callback, application);
    }
}