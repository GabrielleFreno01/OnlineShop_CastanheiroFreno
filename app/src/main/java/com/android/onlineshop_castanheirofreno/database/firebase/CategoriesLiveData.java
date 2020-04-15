package com.android.onlineshop_castanheirofreno.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CategoriesLiveData extends LiveData<CategoryEntity> {

    private static final String TAG = "AccountLiveData";

    private final DatabaseReference reference;
    private final CategoriesLiveData.MyValueEventListener listener = new CategoriesLiveData.MyValueEventListener();

    public CategoriesLiveData(DatabaseReference ref) {
        reference = ref;
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
            CategoryEntity entity = dataSnapshot.getValue(CategoryEntity.class);
            entity.setIdCategory(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
