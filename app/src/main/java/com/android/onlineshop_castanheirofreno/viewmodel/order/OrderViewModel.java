package com.android.onlineshop_castanheirofreno.viewmodel.order;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private Application application;

    private OrderRepository repository;
    private ItemRepository itemRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<OrderEntity> observableOrder;
    private final MediatorLiveData<List<ItemEntity>> observableItems;

    public OrderViewModel(@NonNull Application application,
                          final String orderId, OrderRepository orderRepository, ItemRepository itemRepository) {
        super(application);

        this.application = application;

        repository = orderRepository;
        this.itemRepository = itemRepository;

        observableOrder = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrder.setValue(null);

        if (orderId != null) {
            LiveData<OrderEntity> order = repository.getOrder(orderId);

            // observe the changes of the account entity from the database and forward them
            observableOrder.addSource(order, observableOrder::setValue);
        }
        //LiveData<OrderWithItem> order = repository.getOrderWithItem(orderId, application);

        // observe the changes of the account entity from the database and forward them
       // observableOrder.addSource(order, observableOrder::setValue);

        observableItems = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItems.setValue(null);

       // LiveData<List<ItemEntity>> items = itemRepository.getAllItems(application);

        // observe the changes of the account entity from the database and forward them
       // observableItems.addSource(items, observableItems::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String orderId;

        private final OrderRepository repository;
        private final ItemRepository itemRepository;

        public Factory(@NonNull Application application, String orderId) {
            this.application = application;
            this.orderId = orderId;
            repository = ((BaseApp) application).getOrderRepository();
            itemRepository = ((BaseApp) application).getItemRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderViewModel(application, orderId, repository, itemRepository);
        }
    }


    public LiveData<OrderEntity> getOrderWithItem() {
        return observableOrder;
    }

    public LiveData<List<ItemEntity>> getItems() {
        return observableItems;
    }

    public void createOrder(OrderEntity order, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getOrderRepository()
                .insert(order, callback);
    }

    public void updateOrder(OrderEntity order, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getOrderRepository()
                .update(order, callback);
    }

   /* public void createOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.insert(order, callback, application);
    }

    public void updateOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.update(order, callback, application);
    }*/
}