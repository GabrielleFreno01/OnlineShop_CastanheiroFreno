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
        long id = db.customerDao().insert(client);
    }

    private static void addOrder(final AppDatabase db, final double price, final String creation_date, final String delivery_date, final long idItem, final String status, final String owner) {
        OrderEntity order = new OrderEntity(price, creation_date, delivery_date,  idItem, status, owner );
        long id = db.orderDao().insert(order);
    }

    private static void addItem(final AppDatabase db, final String name, final String description, final int price, final int quantity_in_stock, final long idCategory, final long idImage) {
        ItemEntity item = new ItemEntity(name, description, price,  quantity_in_stock, idCategory, idImage );
        long id = db.itemDao().insert(item);

    }

    private static void addCategory(final AppDatabase db, final String name) {
        CategoryEntity category = new CategoryEntity(name);
        long id = db.categoryDao().insert(category);
    }

    /*private static void addImage(final AppDatabase db, final String lien) {
        ImageEntity image = new ImageEntity(lien);
        long id = db.imageDao().insert(image);
    }*/

    private static void populateWithTestData(AppDatabase db) {
        db.customerDao().deleteAll();

        addCustomer(db, "g@g.com", "Gabrielle", "Freno", "Martigny", 1920, "097900595", "12345");

        addCustomer(db, "t@t.com", "Tiago", "Castanheiro", "Martigny", 1920, "097900595", "56789");

        //Categories
        db.categoryDao().deleteAll();
        addCategory(db, "Audio");

        addCategory(db, "Camera");

        addCategory(db, "Computer");

        addCategory(db, "Gaming");

        addCategory(db, "Smartphone");

        addCategory(db, "TV");

       /* //Images
        db.imageDao().deleteAll();
        addImage(db, ".\\OnlineShop_CastanheiroFreno\\app\\src\\main\\res\\drawable\\acer_aspire_5_599.PNG");

        addImage(db, ".\\OnlineShop_CastanheiroFreno\\app\\src\\main\\res\\drawable\\apple_imac_1499.PNG");*/

        try {
            // Let's ensure that the clients, categories and images are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Items
        db.itemDao().deleteAll();
        addItem(db, "Acer Aspire VX", "Wonderful computer", 950, 2, 4, 2);

        addItem(db, "Apple IMac" , "Perfect for work", 1020, 3, 4, 1);

        try {
            // Let's ensure that the items are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Orders
        db.orderDao().deleteAll();
        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "g@g.com");
        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "g@g.com");
        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "t@t.com");
        addOrder(db, 429, "25.05.2020", "23.03.2020", 1, "In progress", "t@t.com");
        addOrder(db, 429, "26.03.2020", "23.03.2020", 1, "In progress", "t@t.com");

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