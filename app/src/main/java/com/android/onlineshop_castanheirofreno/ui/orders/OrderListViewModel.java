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
import com.android.onlineshop_castanheirofreno.database.pojo.ClientWithOrders;
import com.android.onlineshop_castanheirofreno.database.repository.CustomerRepository;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class OrderListViewModel extends AndroidViewModel {

    private Application application;

    private OrderRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ClientWithOrders>> observableOrderCustomer;
    private final MediatorLiveData<List<OrderEntity>> observableOwnOrder;

    public OrderListViewModel(@NonNull Application application,
                              final String owner,
                              CustomerRepository customerRepository,
                              OrderRepository orderRepository) {
        super(application);

        this.application = application;

        repository = orderRepository;

        observableOrderCustomer = new MediatorLiveData<>();
        observableOwnOrder = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrderCustomer.setValue(null);
        observableOwnOrder.setValue(null);

        LiveData<List<ClientWithOrders>> clientOrders =
                customerRepository.getOtherClientsWithOrders(owner, application);
        LiveData<List<OrderEntity>> ownOrders = repository.getByOwner(owner, application);

        // observe the changes of the entities from the database and forward them
        observableOrderCustomer.addSource(clientOrders, observableOrderCustomer::setValue);
        observableOwnOrder.addSource(ownOrders, observableOwnOrder::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String orderId;

        private final CustomerRepository customerRepository;

        private final OrderRepository orderRepository;

        public Factory(@NonNull Application application, String orderId) {
            this.application = application;
            this.orderId = orderId;
            customerRepository = ((BaseApp) application).getClientRepository();
            orderRepository = ((BaseApp) application).getOrderRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderListViewModel(application, orderId, customerRepository, orderRepository);
        }
    }


    public LiveData<List<ClientWithOrders>> getClientOrders() {
        return observableOrderCustomer;
    }


    public LiveData<List<OrderEntity>> getOwnOrders() {
        return observableOwnOrder;
    }

    public void deleteOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.delete(order, callback, application);
    }


}