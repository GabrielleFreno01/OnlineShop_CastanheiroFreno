package com.android.onlineshop_castanheirofreno.database.async.item;

import android.app.Application;
import android.os.AsyncTask;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class CreateItem extends AsyncTask<ItemEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateItem(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ItemEntity... params) {
        try {
            for (ItemEntity item : params)
                ((BaseApp) application).getDatabase().itemDao()
                        .insert(item);
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