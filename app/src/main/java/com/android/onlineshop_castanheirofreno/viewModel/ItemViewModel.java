package com.android.onlineshop_castanheirofreno.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;

import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;

import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ItemEntity> observableItem;

    public ItemViewModel(@NonNull Application application,
                          final Long id, ItemRepository itemRepository) {
        super(application);

        repository = itemRepository;

        applicationContext = application.getApplicationContext();

        observableItem = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItem.setValue(null);

        LiveData<ItemEntity> item = repository.getItem(id, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableItem.addSource(item, observableItem::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long id;

        private final ItemRepository repository;

        public Factory(@NonNull Application application, Long id) {
            this.application = application;
            this.id = id;
            repository = ItemRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<ItemEntity> getCItem() {
        return observableItem;
    }

    public void createItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.insert(item, callback, applicationContext);
    }

    public void updateItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.update(item, callback, applicationContext);
    }

    public void deleteItem(ItemEntity item, OnAsyncEventListener callback) {
        repository.delete(item, callback, applicationContext);
    }
}