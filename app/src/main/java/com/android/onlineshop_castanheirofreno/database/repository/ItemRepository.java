package com.android.onlineshop_castanheirofreno.database.repository;

import android.app.Application;
import android.content.ClipData;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.AllItemsListLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.CategoryItemsLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.ItemLiveData;
import com.android.onlineshop_castanheirofreno.database.firebase.NewItemsLiveData;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

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
    public LiveData<List<ItemEntity>> getAllItems() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories");
        return new AllItemsListLiveData(reference);
    }

    public LiveData<List<ItemEntity>> getNewItems(Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories");
        return new NewItemsLiveData(reference);
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
    public void update(final ItemEntity newItem, final ItemEntity oldItem, final OnAsyncEventListener callback) {
        //Check if the category has changed
        if(newItem.getIdCategory().equals(oldItem.getIdCategory())) {
            FirebaseDatabase.getInstance()
                    .getReference("categories")
                    .child(newItem.getIdCategory())
                    .child("items")
                    .child(newItem.getIdItem())
                    .updateChildren(newItem.toMap(), (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }else {
            //Delete item under old category
            delete(oldItem, callback);

            //Insert item under new category with the same itemId
            FirebaseDatabase.getInstance()
                    .getReference("categories")
                    .child(newItem.getIdCategory())
                    .child("items")
                    .child(oldItem.getIdItem())
                    .setValue(newItem, (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }
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

    public void insertOrder(OrderEntity newOrder, OnAsyncEventListener callback) {
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
