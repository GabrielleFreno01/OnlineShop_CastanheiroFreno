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
    private final MediatorLiveData<CustomerEntity> observableCustomer;

    public CustomerViewModel(@NonNull Application application,
                           final String user, CustomerRepository customerRepository) {
        super(application);

        this.application = application;

        repository = customerRepository;

        observableCustomer = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCustomer.setValue(null);

        LiveData<CustomerEntity> customer = repository.getCustomerUser(user, application);

        // observe the changes of the customer entity from the database and forward them
        observableCustomer.addSource(customer, observableCustomer::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String user;

        private final CustomerRepository repository;

        public Factory(@NonNull Application application, String user) {
            this.application = application;
            this.user = user;
            repository = ((BaseApp) application).getCustomerRepository();
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CustomerViewModel(application, user, repository);
        }
    }


    public LiveData<CustomerEntity> getCustomer() {
        return observableCustomer;
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
