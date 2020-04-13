package com.android.onlineshop_castanheirofreno.viewmodel.customer;

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
                           final String clientId, CustomerRepository customerRepository) {
        super(application);

        repository = customerRepository;

        observableCustomer = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCustomer.setValue(null);

        LiveData<CustomerEntity> customer = repository.getCustomer(clientId);

        // observe the changes of the client entity from the database and forward them
        observableCustomer.addSource(customer, observableCustomer::setValue);
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
        return observableCustomer;
    }



    public void updateClient(CustomerEntity client, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCustomerRepository()
                .update(client, callback);
    }
    public void updateClientPwd(CustomerEntity client, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCustomerRepository()
                .updatePwd(client, callback);
    }

    public void deleteClient(CustomerEntity client, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCustomerRepository()
                .delete(client, callback);
    }

   /*public void createCustomer(CustomerEntity customer, OnAsyncEventListener callback) {
        repository.insert(customer, callback, application);
    }

    public void updateCustomer(CustomerEntity customer, OnAsyncEventListener callback) {
        repository.update(customer, callback, application);
    }

    public void deleteCustomer(CustomerEntity customer, OnAsyncEventListener callback) {
        repository.delete(customer, callback, application);

    }*/
}
