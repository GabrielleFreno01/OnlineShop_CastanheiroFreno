package com.android.onlineshop_castanheirofreno.viewmodel.order;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CustomerWithOrders;
import com.android.onlineshop_castanheirofreno.database.repository.CustomerRepository;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class OrderListViewModel extends AndroidViewModel {

    private Application application;

    private OrderRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<CustomerWithOrders>> observableOrderCustomer;
    private final MediatorLiveData<List<OrderEntity>> observableOwnOrder;

    public OrderListViewModel(@NonNull Application application,
                              final String ownerId,
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

        /*LiveData<List<CustomerWithOrders>> clientOrders = customerRepository.getCustomerWithOrders(ownerId);
        LiveData<List<OrderWithItem>> ownOrders = repository.getOwnedOrdersWithItem(ownerId);

        // observe the changes of the entities from the database and forward them
        observableOrderCustomer.addSource(clientOrders, observableOrderCustomer::setValue);
        observableOwnOrder.addSource(ownOrders, observableOwnOrder::setValue);*/

        LiveData<List<CustomerWithOrders>> customerOrders = customerRepository.getCustomerWithOrders(ownerId);
        LiveData<List<OrderEntity>> ownOrders = repository.getByOwner(ownerId);

        // observe the changes of the entities from the database and forward them
        observableOrderCustomer.addSource(customerOrders, observableOrderCustomer::setValue);
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
            customerRepository = ((BaseApp) application).getCustomerRepository();
            orderRepository = ((BaseApp) application).getOrderRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderListViewModel(application, orderId, customerRepository, orderRepository);
        }
    }


    public LiveData<List<CustomerWithOrders>> getClientOrders() {
        return observableOrderCustomer;
    }


    public LiveData<List<OrderEntity>> getOwnAccounts() {
        return observableOwnOrder;
    }

    public void deleteOrder(OrderEntity order, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getOrderRepository()
                .delete(order, callback);
    }


}
