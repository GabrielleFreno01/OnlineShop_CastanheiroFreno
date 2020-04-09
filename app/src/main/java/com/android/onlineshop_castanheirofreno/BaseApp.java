package com.android.onlineshop_castanheirofreno;


import android.app.Application;

import com.android.onlineshop_castanheirofreno.database.repository.CategoryRepository;
import com.android.onlineshop_castanheirofreno.database.repository.CustomerRepository;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;


public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

   /* public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }
*/
    public OrderRepository getOrderRepository() {
        return OrderRepository.getInstance();
    }

    public ItemRepository getItemRepository() {
        return ItemRepository.getInstance();
    }

    public CustomerRepository getCustomerRepository() {
        return CustomerRepository.getInstance();
    }

    public CategoryRepository getCategoryRepository() {
        return CategoryRepository.getInstance();
    }


}