package com.android.onlineshop_castanheirofreno.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.CategoryItemsLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.ItemLiveData;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemRepository {

    private static ItemRepository instance;

    private ItemRepository() {
    }

    public static ItemRepository getInstance() {
        if (instance == null) {
            synchronized (ItemRepository.class) {
                if (instance == null) {
                    instance = new ItemRepository();
                }
            }
        }
        return instance;
    }

   public LiveData<ItemEntity> getItem(Context context, String idItem, String idCategory) {
       DatabaseReference reference = FirebaseDatabase.getInstance()
               .getReference("categories")
               .child(idCategory)
               .child("items")
               .child(idItem);
       return new ItemLiveData(reference, idCategory);
    }

    public void insert(final ItemEntity item, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(item.getIdCategory())
                .child("items");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(item.getIdCategory())
                .child("items")
                .child(key)
                .setValue(item, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
    public void update(final ItemEntity item, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(item.getIdCategory())
                .child("items")
                .child(item.getIdItem())
                .updateChildren(item.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ItemEntity item, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(item.getIdCategory())
                .child("items")
                .child(item.getIdItem())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /*
    public LiveData<List<ItemEntity>> getItemsByCategory(final String id, Context context) {
        return AppDatabase.getInstance(context).itemDao().getItemsByCategory(id);
    }

    public LiveData<List<ItemEntity>> getAllItems(Context context) {
        return AppDatabase.getInstance(context).itemDao().getAll();
    }

    public LiveData<List<ItemEntity>> getNewItems(Context context) {
        return AppDatabase.getInstance(context).itemDao().getNewItems();
    }

    public void insert(final ItemEntity item, OnAsyncEventListener callback,
                       Application application) {
        new CreateItem(application, callback).execute(item);
    }

    public void update(final ItemEntity item, OnAsyncEventListener callback,
                       Application application) {
        new UpdateItem(application, callback).execute(item);
    }

    public void delete(final ItemEntity item, OnAsyncEventListener callback,
                       Application application) {
        new DeleteItem(application, callback).execute(item);
    }
*/
}
