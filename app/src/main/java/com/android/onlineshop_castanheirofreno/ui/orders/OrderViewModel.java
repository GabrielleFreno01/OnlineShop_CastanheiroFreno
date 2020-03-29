package com.android.onlineshop_castanheirofreno.ui.orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private Application application;

    private OrderRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<OrderEntity> observableOrder;

    public OrderViewModel(@NonNull Application application,
                          final String owner, OrderRepository orderRepository) {
        super(application);

        this.application = application;

        repository = orderRepository;

        observableOrder = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrder.setValue(null);

        LiveData<List<OrderEntity>> order = repository.getOrders(application);

        // observe the changes of the account entity from the database and forward them
        //observableOrder.addSource(order, observableOrder::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String owner;

        private final OrderRepository repository;

        public Factory(@NonNull Application application, String owner) {
            this.application = application;
            this.owner = owner;
            repository = ((BaseApp) application).getOrderRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderViewModel(application, owner, repository);
        }
    }


    public LiveData<OrderEntity> getOrder() {
        return observableOrder;
    }

    public void createOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.insert(order, callback, application);
    }

    public void updateOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.update(order, callback, application);
    }
}