package com.android.onlineshop_castanheirofreno.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.AppDatabase;
import com.android.onlineshop_castanheirofreno.database.async.CreateItem;
import com.android.onlineshop_castanheirofreno.database.async.CreateOrder;
import com.android.onlineshop_castanheirofreno.database.async.DeleteItem;
import com.android.onlineshop_castanheirofreno.database.async.DeleteOrder;
import com.android.onlineshop_castanheirofreno.database.async.UpdateItem;
import com.android.onlineshop_castanheirofreno.database.async.UpdateOrder;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.List;

public class ItemRepository {

    private static ItemRepository instance;

    private ItemRepository() {}

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

    public LiveData<ItemEntity> getItem(final long id, Context context) {
        return AppDatabase.getInstance(context).itemDao().getById(id);
    }

    public LiveData<List<ItemEntity>> getAllItems(Context context) {
        return AppDatabase.getInstance(context).itemDao().getAll();
    }

    public void insert(final ItemEntity item, OnAsyncEventListener callback, Context context) {
        new CreateItem(context, callback).execute(item);
    }

    public void update(final ItemEntity item, OnAsyncEventListener callback, Context context) {
        new UpdateItem(context, callback).execute(item);
    }

    public void delete(final ItemEntity item, OnAsyncEventListener callback, Context context) {
        new DeleteItem(context, callback).execute(item);
    }
}
