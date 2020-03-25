package com.android.onlineshop_castanheirofreno.viewModel;

import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;


public class OrderListViewModel extends AndroidViewModel {

    private OrderRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<OrderEntity>> observableOrder;

    public OrderListViewModel(@NonNull Application application, OrderRepository orderRepository) {
        super(application);

        repository = orderRepository;

        applicationContext = application.getApplicationContext();

        observableOrder = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrder.setValue(null);

        LiveData<List<OrderEntity>> clients = repository.getAllOrders(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableOrder.addSource(clients, observableOrder::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final OrderRepository orderRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            orderRepository = OrderRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderListViewModel(application, orderRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<OrderEntity>> getClients() {
        return observableOrder;
    }
}
