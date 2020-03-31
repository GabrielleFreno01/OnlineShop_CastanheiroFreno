package com.android.onlineshop_castanheirofreno.database.async.customer;

import android.app.Application;
import android.os.AsyncTask;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;


public class CreateCustomer extends AsyncTask<CustomerEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateCustomer(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CustomerEntity... params) {
        try {
            for (CustomerEntity client : params)
                ((BaseApp) application).getDatabase().customerDao()
                        .insert(client);
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
