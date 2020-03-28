package com.android.onlineshop_castanheirofreno.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.async.customer.CreateCustomer;
import com.android.onlineshop_castanheirofreno.database.async.customer.DeleteCustomer;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.ClientWithOrders;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class CustomerRepository {

    private static CustomerRepository instance;

    private CustomerRepository() {
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new CustomerRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CustomerEntity> getClient(final String clientId, Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getById(clientId);
    }

    public LiveData<List<ClientWithOrders>> getOtherClientsWithOrders(final String owner,
                                                                        Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getOtherClientsWithOrders(owner);
    }

    public void insert(final CustomerEntity client, OnAsyncEventListener callback,
                       Application application) {
        new CreateCustomer(application, callback).execute(client);
    }


    public void delete(final CustomerEntity client, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCustomer(application, callback).execute(client);
    }
}
