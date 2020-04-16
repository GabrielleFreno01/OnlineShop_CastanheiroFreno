package com.android.onlineshop_castanheirofreno.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ItemLiveData extends LiveData<ItemEntity> {

    private static final String TAG = "ItemLiveData";

    private final DatabaseReference reference;
    private final String idCategory;
    private final ItemLiveData.MyValueEventListener listener = new ItemLiveData.MyValueEventListener();

    public ItemLiveData(DatabaseReference ref, String idCategory) {
        reference = ref;
        this.idCategory = idCategory;
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
                ItemEntity entity = dataSnapshot.getValue(ItemEntity.class);
                entity.setIdItem(dataSnapshot.getKey());
                entity.setIdCategory(idCategory);
                setValue(entity);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
