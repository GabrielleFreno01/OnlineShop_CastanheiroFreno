package com.android.onlineshop_castanheirofreno.database.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.OrderListLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.OrderLiveData;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

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

    public void update(final OrderEntity account, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(account.getOwner())
                .child("accounts")
                .child(account.getIdOrder())
                .updateChildren(account.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final OrderEntity account, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(account.getOwner())
                .child("accounts")
                .child(account.getIdOrder())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void transaction(final OrderEntity sender, final OrderEntity recipient,
                            OnAsyncEventListener callback) {
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                rootReference
                        .child("clients")
                        .child(sender.getOwner())
                        .child("accounts")
                        .child(sender.getIdOrder())
                        .updateChildren(sender.toMap());

                rootReference
                        .child("clients")
                        .child(recipient.getOwner())
                        .child("accounts")
                        .child(recipient.getIdOrder())
                        .updateChildren(recipient.toMap());

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    callback.onFailure(databaseError.toException());
                } else {
                    callback.onSuccess();
                }
            }
        });
    }
/*
    public LiveData<OrderEntity> getOrder(final Long orderId, Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getById(orderId);
    }

    public LiveData<OrderWithItem> getOrderWithItem(final Long orderId, Application application) {
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