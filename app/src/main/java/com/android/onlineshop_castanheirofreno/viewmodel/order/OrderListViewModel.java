package com.android.onlineshop_castanheirofreno.viewmodel.order;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class OrderListViewModel extends AndroidViewModel {

    private Application application;

    private OrderRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions
    private final MediatorLiveData<List<OrderWithItem>> observableOwnOrder;

    public OrderListViewModel(@NonNull Application application,
                              final String ownerId,
                              OrderRepository orderRepository) {
        super(application);

        this.application = application;

        repository = orderRepository;

        observableOwnOrder = new MediatorLiveData<>();
        // set by default null, until we get data from the database
        observableOwnOrder.setValue(null);

        //LiveData<List<CustomerWithOrders>> clientOrders = customerRepository.getCustomerWithOrders(ownerId, application);
        LiveData<List<OrderWithItem>> ownOrders = repository.getOrdersWithItem(ownerId);

        // observe the changes of the entities from the database and forward them
        observableOwnOrder.addSource(ownOrders, observableOwnOrder::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String ownerId;

        private final OrderRepository orderRepository;

        public Factory(@NonNull Application application, String ownerId) {
            this.application = application;
            this.ownerId = ownerId;
            orderRepository = ((BaseApp) application).getOrderRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderListViewModel(application, ownerId, orderRepository);
        }
    }

    public LiveData<List<OrderWithItem>> getOwnOrders() {
        return observableOwnOrder;
    }

    public void deleteOrder(OrderWithItem orderWithItem, OnAsyncEventListener callback) {
        repository.delete(orderWithItem.order, callback);
    }


}
