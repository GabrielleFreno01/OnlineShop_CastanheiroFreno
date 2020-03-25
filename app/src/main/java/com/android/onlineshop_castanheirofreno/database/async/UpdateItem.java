package com.android.onlineshop_castanheirofreno.database.async;

import android.content.Context;
import android.os.AsyncTask;

import com.android.onlineshop_castanheirofreno.database.AppDatabase;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class UpdateItem extends AsyncTask<ItemEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateItem(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ItemEntity... params) {
        try {
            for (ItemEntity item : params)
                database.itemDao().update(item);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}