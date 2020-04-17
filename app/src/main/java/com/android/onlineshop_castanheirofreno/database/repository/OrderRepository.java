package com.android.onlineshop_castanheirofreno.database.repository;

import android.content.ClipData;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.OrderListLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.OrderLiveData;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

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

    public LiveData<OrderWithItem> getOrderWithItem(final String owner, final String orderId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return new OrderLiveData(reference, owner, orderId);
    }

    public LiveData<List<OrderWithItem>> getOrdersWithItem(final String owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return new OrderListLiveData(reference, owner);
    }

    public void insert(OrderEntity newOrder, OnAsyncEventListener callback) {
        newOrder.setOwner(FirebaseAuth.getInstance().getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(newOrder.getOwner());
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(newOrder.getOwner())
                .child(key)
                .setValue(newOrder, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final OrderEntity entity, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(entity.getOwner())
                .child(entity.getIdOrder())
                .updateChildren(entity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final OrderEntity entity, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(entity.getOwner())
                .child(entity.getIdOrder())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void deleteCorrespondingOrders(ItemEntity entity, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot customerSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : customerSnapshot.getChildren()) {
                        if(((String)orderSnapshot.child("idItem").getValue()).equals(entity.getIdItem())) {
                            FirebaseDatabase.getInstance().getReference("orders")
                                    .child(customerSnapshot.getKey())
                                    .child(orderSnapshot.getKey())
                                    .removeValue((databaseError, databaseReference) -> {
                                        if (databaseError != null) {
                                            callback.onFailure(databaseError.toException());
                                        } else {
                                            callback.onSuccess();
                                        }
                                    });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });



    }

    public void changeCategory(ItemEntity entity, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot customerSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : customerSnapshot.getChildren()) {
                        if(orderSnapshot.child("idItem").getValue().equals(entity.getIdItem())) {
                            FirebaseDatabase.getInstance().getReference("orders")
                                    .child(customerSnapshot.getKey())
                                    .child(orderSnapshot.getKey())
                                    .child("idCategory")
                                    .setValue(entity.getIdCategory())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            callback.onSuccess();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            callback.onFailure(e);
                                        }
                                    });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });
    }
}