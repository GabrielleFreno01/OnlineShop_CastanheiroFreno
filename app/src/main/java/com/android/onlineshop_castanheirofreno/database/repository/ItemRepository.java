package com.android.onlineshop_castanheirofreno.database.repository;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import com.android.onlineshop_castanheirofreno.database.AppDatabase;
import com.android.onlineshop_castanheirofreno.database.async.item.CreateItem;
import com.android.onlineshop_castanheirofreno.database.async.item.DeleteItem;
import com.android.onlineshop_castanheirofreno.database.async.item.UpdateItem;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
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

    public LiveData<List<ItemEntity>> getItemsByCategory(final long id, Context context) {
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

}
