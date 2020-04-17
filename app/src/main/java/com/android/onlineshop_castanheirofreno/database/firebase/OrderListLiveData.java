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
import java.util.ArrayList;
import java.util.List;

public class OrderListLiveData extends LiveData<List<OrderWithItem>> {

    private static final String TAG = "OrderListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final MyValueEventListener listener = new MyValueEventListener();

    public OrderListLiveData(DatabaseReference ref, String owner) {
        reference = ref;
        this.owner = owner;
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
            setValue(toOrders(dataSnapshot, owner));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<OrderWithItem> toOrders(DataSnapshot snapshot, String owner) {
        List<OrderWithItem> orderWithItemList = new ArrayList<>();
        DataSnapshot ordersSnapshot = snapshot.child("orders").child(owner);
        for (DataSnapshot childSnapshot : ordersSnapshot.getChildren()) {
            OrderWithItem orderWithItem = new OrderWithItem();
            orderWithItem.order = childSnapshot.getValue(OrderEntity.class);
            if (orderWithItem.order != null) {
                orderWithItem.order.setIdOrder(childSnapshot.getKey());
                orderWithItem.order.setOwner(owner);
                orderWithItem.item = toItem(snapshot, orderWithItem.order.getIdItem(), orderWithItem.order.getIdCategory());
                orderWithItemList.add(orderWithItem);
            }
        }
        return orderWithItemList;
    }

    private ItemEntity toItem(DataSnapshot snapshot, String idItem, String idCategory) {
        DataSnapshot childSnapshot = snapshot.child("categories").child(idCategory).child("items").child(idItem);
        ItemEntity entity = childSnapshot.getValue(ItemEntity.class);
        if(entity!=null) {
            entity.setIdItem(childSnapshot.getKey());
            entity.setIdCategory(idCategory);
        }
        return entity;
    }
}