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
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private Application application;

    private OrderRepository repository;
    private ItemRepository itemRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<OrderWithItem> observableOrder;
    private final MediatorLiveData<List<ItemEntity>> observableItems;

    public OrderViewModel(@NonNull Application application,
                          final String orderId, OrderRepository orderRepository, ItemRepository itemRepository) {
        super(application);

        this.application = application;

        repository = orderRepository;
        this.itemRepository = itemRepository;

        observableOrder = new MediatorLiveData<>();
        observableItems = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableOrder.setValue(null);
        observableItems.setValue(null);

        LiveData<OrderWithItem> order = repository.getOrderWithItem(FirebaseAuth.getInstance().getUid(),orderId);
        LiveData<List<ItemEntity>> items = itemRepository.getAllItems();

        // observe the changes of the account entity from the database and forward them
        observableOrder.addSource(order, observableOrder::setValue);
        observableItems.addSource(items, observableItems::setValue);
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


    public LiveData<OrderWithItem> getOrderWithItem() {
        return observableOrder;
    }

    public LiveData<List<ItemEntity>> getItems() {
        return observableItems;
    }

    public void updateOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.update(order, callback);
    }
}