package com.android.onlineshop_castanheirofreno.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<OrderEntity> observableOrder;

    public OrderViewModel(@NonNull Application application,
                           final Long id, OrderRepository clientRepository) {
        super(application);

        repository = clientRepository;

        applicationContext = application.getApplicationContext();

        observableOrder = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrder.setValue(null);

        LiveData<OrderEntity> order = repository.getOrder(id, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableOrder.addSource(order, observableOrder::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long id;

        private final OrderRepository repository;

        public Factory(@NonNull Application application, Long id) {
            this.application = application;
            this.id = id;
            repository = OrderRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<OrderEntity> getClient() {
        return observableOrder;
    }

    public void createClient(OrderEntity client, OnAsyncEventListener callback) {
        repository.insert(client, callback, applicationContext);
    }

    public void updateClient(OrderEntity client, OnAsyncEventListener callback) {
        repository.update(client, callback, applicationContext);
    }

    public void deleteClient(OrderEntity client, OnAsyncEventListener callback) {
        repository.delete(client, callback, applicationContext);
    }
}
