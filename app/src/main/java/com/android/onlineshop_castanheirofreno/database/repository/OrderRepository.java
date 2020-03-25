package com.android.onlineshop_castanheirofreno.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.AppDatabase;
import com.android.onlineshop_castanheirofreno.database.async.CreateOrder;
import com.android.onlineshop_castanheirofreno.database.async.DeleteOrder;
import com.android.onlineshop_castanheirofreno.database.async.UpdateOrder;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class OrderRepository {

    private static OrderRepository instance;

    private OrderRepository() {}

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

    public LiveData<OrderEntity> getOrder(final long id, Context context) {
        return AppDatabase.getInstance(context).orderDao().getById(id);
}

    public LiveData<List<OrderEntity>> getAllOrders(Context context) {
        return AppDatabase.getInstance(context).orderDao().getAll();
    }

    public void insert(final OrderEntity order, OnAsyncEventListener callback, Context context) {
        new CreateOrder(context, callback).execute(order);
    }

    public void update(final OrderEntity order, OnAsyncEventListener callback, Context context) {
        new UpdateOrder(context, callback).execute(order);
    }

    public void delete(final OrderEntity order, OnAsyncEventListener callback, Context context) {
        new DeleteOrder(context, callback).execute(order);
    }
}
