package com.android.onlineshop_castanheirofreno.database.async.order;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.AppDatabase;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class UpdateOrder extends AsyncTask<OrderEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateOrder(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(OrderEntity... params) {
        try {
            for (OrderEntity order : params)
                ((BaseApp) application).getDatabase().orderDao()
                        .update(order);
        } catch (Exception e) {
            this.exception = e;
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