package com.android.onlineshop_castanheirofreno.ui.item;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class ItemViewModel  extends AndroidViewModel {

    private Application application;

    private ItemRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ItemEntity> observableAccount;

    public ItemViewModel(@NonNull Application application,
                            final Long idItem, ItemRepository accountRepository) {
        super(application);

        this.application = application;

        repository = accountRepository;

        observableAccount = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableAccount.setValue(null);

        LiveData<ItemEntity> item = repository.getItem(idItem, application);

        // observe the changes of the account entity from the database and forward them
        observableAccount.addSource(item, observableAccount::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long accountId;

        private final ItemRepository repository;

        public Factory(@NonNull Application application, Long accountId) {
            this.application = application;
            this.accountId = accountId;
            repository = ((BaseApp) application).getItemRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, accountId, repository);
        }
    }

    /**
     * Expose the LiveData AccountEntity query so the UI can observe it.
     */
    public LiveData<ItemEntity> getAccount() {
        return observableAccount;
    }

    public void createAccount(ItemEntity account, OnAsyncEventListener callback) {
        repository.insert(account, callback, application);
    }

    public void updateAccount(ItemEntity account, OnAsyncEventListener callback) {
        repository.update(account, callback, application);
    }
}