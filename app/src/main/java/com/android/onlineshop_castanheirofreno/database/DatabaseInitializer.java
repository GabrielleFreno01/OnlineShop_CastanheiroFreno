package com.android.onlineshop_castanheirofreno.database;

import android.os.AsyncTask;
import android.util.Log;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ImageEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;

import java.text.DateFormat;


public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addCustomer(final AppDatabase db, final String email, final String firstName,
                                    final String lastName, final String city, final int city_code, final String telephone, final String password) {
        CustomerEntity client = new CustomerEntity(email, firstName, lastName, city, city_code, telephone, password);
        db.customerDao().insert(client);
    }

    private static void addOrder(final AppDatabase db, final double price, final String creation_date, final String delivery_date, final long idItem, final String status, final String owner) {
        OrderEntity order = new OrderEntity(price, creation_date, delivery_date,  idItem, status, owner );
        db.orderDao().insert(order);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.customerDao().deleteAll();

        addCustomer(db, "g@g.com", "Gabrielle", "Freno", "Martigny", 1920, "097900595", "12345");

        addCustomer(db, "t@t.com", "Tiago", "Castanheiro", "Martigny", 1920, "097900595", "56789");


        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "g@g.com");

        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "g@g.com");

        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "t@t.com");


    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
