package com.android.onlineshop_castanheirofreno.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CustomerWithOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrderListLiveData extends LiveData<List<CustomerWithOrders>> {

    private static final String TAG = "CustomerOrderLLD";

    private final DatabaseReference reference;
    private final String owner;
    private final CustomerOrderListLiveData.MyValueEventListener listener =
            new CustomerOrderListLiveData.MyValueEventListener();

    public CustomerOrderListLiveData(DatabaseReference ref, String owner) {
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
            setValue(toCustomerWithOrdersList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "ERROR" + reference, databaseError.toException());
        }
    }

    private List<CustomerWithOrders> toCustomerWithOrdersList(DataSnapshot snapshot) {
        List<CustomerWithOrders> customerWithOrdersList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().equals(owner)) {
                CustomerWithOrders customerWithOrder = new CustomerWithOrders();
                customerWithOrder.client = childSnapshot.getValue(CustomerEntity.class);
                customerWithOrder.client.setIdCustomer(childSnapshot.getKey());
                customerWithOrder.orders = toOrders(childSnapshot.child("orders"),
                        childSnapshot.getKey());
                customerWithOrdersList.add(customerWithOrder);
            }
        }
        return customerWithOrdersList;
    }

    private List<OrderEntity> toOrders(DataSnapshot snapshot, String ownerId) {
        List<OrderEntity> orders = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            OrderEntity entity = childSnapshot.getValue(OrderEntity.class);
            entity.setIdOrder(childSnapshot.getKey());
            entity.setOwner(ownerId);
            orders.add(entity);
        }
        return orders;
    }

}
