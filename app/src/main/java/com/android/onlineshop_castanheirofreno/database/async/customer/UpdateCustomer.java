package com.android.onlineshop_castanheirofreno.database.async.customer;

import android.app.Application;
import android.os.AsyncTask;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class UpdateCustomer extends AsyncTask<CustomerEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdateCustomer(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(CustomerEntity... params) {
        try {
            for (CustomerEntity client : params)
                ((BaseApp) application).getDatabase().customerDao()
                        .update(client);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}