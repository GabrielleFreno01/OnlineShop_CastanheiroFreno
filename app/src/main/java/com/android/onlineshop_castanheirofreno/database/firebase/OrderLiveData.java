package com.android.onlineshop_castanheirofreno.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class OrderLiveData extends LiveData<OrderWithItem> {

    private static final String TAG = "AccountLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final String idOrder;
    private final OrderLiveData.MyValueEventListener listener = new OrderLiveData.MyValueEventListener();

    public OrderLiveData(DatabaseReference ref, String owner, String idOrder) {
        reference = ref;
        this.owner = owner;
        this.idOrder = idOrder;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            OrderWithItem entity = new OrderWithItem();
            if(entity != null) {
                DataSnapshot orderSnapshot = dataSnapshot.child("orders").child(owner).child(idOrder);
                entity.order = orderSnapshot.getValue(OrderEntity.class);
                entity.order.setIdOrder(idOrder);
                entity.order.setOwner(owner);
                entity.item = toItem(dataSnapshot, entity.order.getIdItem(), entity.order.getIdCategory());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private ItemEntity toItem(DataSnapshot dataSnapshot, String idItem, String idCategory) {
        DataSnapshot childSnapshot = dataSnapshot.child("categories").child(idCategory).child("items").child(idItem);
        ItemEntity entity = childSnapshot.getValue(ItemEntity.class);
        entity.setIdItem(childSnapshot.getKey());
        entity.setIdCategory(idCategory);
        return entity;
    }
}
