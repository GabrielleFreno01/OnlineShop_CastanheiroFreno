package com.android.onlineshop_castanheirofreno.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.AppDatabase;
import com.android.onlineshop_castanheirofreno.database.async.order.CreateOrder;
import com.android.onlineshop_castanheirofreno.database.async.order.DeleteOrder;
import com.android.onlineshop_castanheirofreno.database.async.order.UpdateOrder;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class OrderRepository {

    private static OrderRepository instance;

    private OrderRepository() {

    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<OrderEntity> getOrder(final Long orderId, Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getById(orderId);
    }


    public LiveData<List<OrderEntity>> getOrders(Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getAll();
    }

    public LiveData<List<OrderEntity>> getByOwner(final String owner, Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getOwned(owner);
    }

    public void insert(final OrderEntity order, OnAsyncEventListener callback,
                       Application application) {
        new CreateOrder(application, callback).execute(order);
    }

    public void update(final OrderEntity order, OnAsyncEventListener callback,
                       Application application) {
        new UpdateOrder(application, callback).execute(order);
    }

    public void delete(final OrderEntity order, OnAsyncEventListener callback,
                       Application application) {
        new DeleteOrder(application, callback).execute(order);
    }



}
