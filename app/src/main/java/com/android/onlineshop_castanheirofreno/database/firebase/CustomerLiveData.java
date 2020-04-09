package com.android.onlineshop_castanheirofreno.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CustomerLiveData extends LiveData<CustomerEntity> {
    private static final String TAG = "ClientLiveData";

    private final DatabaseReference reference;
    private final CustomerLiveData.MyValueEventListener listener = new CustomerLiveData.MyValueEventListener();

    public CustomerLiveData(DatabaseReference ref) {
        this.reference = ref;
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
            CustomerEntity entity = dataSnapshot.getValue(CustomerEntity.class);
            entity.setIdCustomer(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
