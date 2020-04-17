package com.android.onlineshop_castanheirofreno.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllItemsListLiveData extends LiveData<List<ItemEntity>> {

    private static final String TAG = "AllItemsListLiveData";

    private final DatabaseReference reference;
    private final AllItemsListLiveData.MyValueEventListener listener = new AllItemsListLiveData.MyValueEventListener();

    public AllItemsListLiveData(DatabaseReference ref) {
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
            setValue(toItems(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ItemEntity> toItems(DataSnapshot snapshot) {
        List<ItemEntity> allItems = new ArrayList<>();
        for (DataSnapshot categorySnapshot : snapshot.getChildren()){
            String categoryKey = categorySnapshot.getKey();
            DataSnapshot itemsSnapshot = categorySnapshot.child("items");
            for (DataSnapshot childSnapshot : itemsSnapshot.getChildren()) {
                ItemEntity entity = childSnapshot.getValue(ItemEntity.class);
                if(entity!=null) {
                    entity.setIdItem(childSnapshot.getKey());
                    entity.setIdCategory(categoryKey);
                    allItems.add(entity);
                }
            }
        }

        Collections.sort(allItems, new Comparator<ItemEntity>() {
            @Override
            public int compare(ItemEntity o1, ItemEntity o2) {
                return o1.getIdItem().compareTo(o2.getIdItem());
            }
        });

        return allItems;
    }
}
