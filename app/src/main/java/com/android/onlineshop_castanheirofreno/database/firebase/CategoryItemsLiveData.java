package com.android.onlineshop_castanheirofreno.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CategoryWithItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemsLiveData extends LiveData<CategoryWithItems> {

    private static final String TAG = "CategoryItemsLiveData";

    private final DatabaseReference reference;
    private final CategoryItemsLiveData.MyValueEventListener listener =
            new CategoryItemsLiveData.MyValueEventListener();

    public CategoryItemsLiveData(DatabaseReference ref) {
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
            setValue(toCategoryWithItems(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private CategoryWithItems toCategoryWithItems(DataSnapshot snapshot) {
        CategoryWithItems categoryWithItems = new CategoryWithItems();
        categoryWithItems.category = snapshot.getValue(CategoryEntity.class);
        categoryWithItems.category.setIdCategory(snapshot.getKey());
        categoryWithItems.items = toItems(snapshot.child("items"),
                snapshot.getKey());


        return categoryWithItems;
    }

    private List<ItemEntity> toItems(DataSnapshot snapshot, String categoryId) {
        List<ItemEntity> items = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ItemEntity entity = childSnapshot.getValue(ItemEntity.class);
            entity.setIdItem(childSnapshot.getKey());
            entity.setIdCategory(categoryId);
            items.add(entity);
        }
        return items;
    }
}
