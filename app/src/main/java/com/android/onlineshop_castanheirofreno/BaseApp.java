package com.android.onlineshop_castanheirofreno;



import android.app.Application;

import com.android.onlineshop_castanheirofreno.database.AppDatabase;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public ItemRepository getAccountRepository() {
        return ItemRepository.getInstance();
    }

    public OrderRepository getClientRepository() {
        return OrderRepository.getInstance();
    }
}