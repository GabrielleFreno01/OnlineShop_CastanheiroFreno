package com.android.onlineshop_castanheirofreno.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.async.customer.CreateCustomer;
import com.android.onlineshop_castanheirofreno.database.async.customer.DeleteCustomer;
import com.android.onlineshop_castanheirofreno.database.async.customer.UpdateCustomer;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CustomerWithOrders;
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

    public LiveData<CustomerEntity> getCustomer(final long customerId, Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getById(customerId);
    }

    public LiveData<CustomerEntity> getCustomerUser(final String user, Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getByEmail(user);
    }

    public LiveData<CustomerEntity> getCustomerByEmail(final String email, Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getByEmail(email);
    }

    public LiveData<List<CustomerWithOrders>> getCustomerWithOrders(final String owner,
                                                                  Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getOtherCustomersWithOrders(owner);
    }

    public void insert(final CustomerEntity customer, OnAsyncEventListener callback,
                       Application application) {
        new CreateCustomer(application, callback).execute(customer);
    }

    public void update(final CustomerEntity customer, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCustomer(application, callback).execute(customer);
    }

    public void delete(final CustomerEntity customer, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCustomer(application, callback).execute(customer);
    }


}
