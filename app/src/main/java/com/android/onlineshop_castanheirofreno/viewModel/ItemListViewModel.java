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


import java.util.List;

public class ItemListViewModel extends AndroidViewModel {

    private ItemRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ItemEntity>> observableItem;

    public ItemListViewModel(@NonNull Application application, ItemRepository itemRepository) {
        super(application);

        repository = itemRepository;

        applicationContext = application.getApplicationContext();

        observableItem = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItem.setValue(null);

        LiveData<List<ItemEntity>> items = repository.getAllItems(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableItem.addSource(items, observableItem::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ItemRepository itemRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            itemRepository = ItemRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemListViewModel(application, itemRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<ItemEntity>> getClients() {
        return observableItem;
    }
}
