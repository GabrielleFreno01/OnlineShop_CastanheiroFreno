package com.android.onlineshop_castanheirofreno.database.repository;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.OrderItemLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.OrderListLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.OrderLiveData;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public LiveData<OrderEntity> getOrder(final String orderId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Customers")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Orders")
                .child(orderId);
        return new OrderLiveData(reference);
    }

    public LiveData<List<OrderWithItem>> getOwnedOrdersWithItem(final String ownerId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(ownerId);
        return new OrderItemLiveData(reference, ownerId);
    }


    public LiveData<List<OrderEntity>> getByOwner(final String owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(owner)
                .child("Orders");
        return new OrderListLiveData(reference, owner);
    }

    public void insert(final OrderEntity account, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(account.getOwner())
                .child("accounts");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(account.getOwner())
                .child("accounts")
                .child(key)
                .setValue(account, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final OrderEntity order, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(order.getOwner())
                .child("order")
                .child(order.getIdOrder())
                .updateChildren(order.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final OrderEntity account, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(account.getOwner())
                .child("order")
                .child(account.getIdOrder())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


/*
    public LiveData<OrderEntity> getOrder(final String orderId, Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getById(orderId);
    }

    public LiveData<OrderWithItem> getOrderWithItem(final String orderId, Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getOrderWithItem(orderId);
    }

    public LiveData<List<OrderEntity>> getOrders(Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getAll();
    }

    public LiveData<List<OrderWithItem>> getOwnedOrdersWithItem(final String owner, Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getOwnedOrdersWithItem(owner);
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
*/

}