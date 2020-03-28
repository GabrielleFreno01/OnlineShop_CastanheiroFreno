package com.android.onlineshop_castanheirofreno.ui.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.repository.CustomerRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class CustomerViewModel extends AndroidViewModel {

    private CustomerRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<CustomerEntity> observableCustomert;

    public CustomerViewModel(@NonNull Application application,
                           final String customerId, CustomerRepository customerRepository) {
        super(application);

        this.application = application;

        repository = customerRepository;

        observableCustomert = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCustomert.setValue(null);

        LiveData<CustomerEntity> customer = repository.getCustomer(customerId, application);

        // observe the changes of the customer entity from the database and forward them
        observableCustomert.addSource(customer, observableCustomert::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String customerId;

        private final CustomerRepository repository;

        public Factory(@NonNull Application application, String customerId) {
            this.application = application;
            this.customerId = customerId;
            repository = ((BaseApp) application).getCustomerRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CustomerViewModel(application, customerId, repository);
        }
    }


    public LiveData<CustomerEntity> getCustomer() {
        return observableCustomert;
    }

    public void createCustomer(CustomerEntity customer, OnAsyncEventListener callback) {
        repository.insert(customer, callback, application);
    }

    public void updateCustomer(CustomerEntity customer, OnAsyncEventListener callback) {
        repository.update(customer, callback, application);
    }

    public void deleteCustomer(CustomerEntity customer, OnAsyncEventListener callback) {
        repository.delete(customer, callback, application);

    }
}
