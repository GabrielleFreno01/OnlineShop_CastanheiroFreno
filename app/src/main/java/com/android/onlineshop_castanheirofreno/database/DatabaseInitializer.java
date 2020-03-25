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

    private static void addOrder(final AppDatabase db, double price, String creation_date,String delivery_date, long idItem, String status, long idCustomer) {
        OrderEntity order = new OrderEntity(price, creation_date,  delivery_date, idItem, status, idCustomer);
        db.orderDao().insert(order);
    }

    private static void addItem(final AppDatabase db, String name, String description, int price, int quantity_in_stock, long idCategory, long idImage){
        ItemEntity item = new ItemEntity(name, description, price, quantity_in_stock, idCategory, idImage);
        db.itemDao().insert(item);
    }

    private static void addCategory(final AppDatabase db, String name){
        CategoryEntity category = new CategoryEntity(name);
        db.categoryDao().insert(category);
    }

    private static void addImage(final AppDatabase db, String lien){
        ImageEntity image = new ImageEntity(lien);
        db.imageDao().insert(image);
    }

    private static void addCustomer(final AppDatabase db, String firstname, String lastname, String address, String city, int city_code, String telephone, String email, long idOrder){
        CustomerEntity customer = new CustomerEntity(firstname, lastname, address, city, city_code, telephone, email, idOrder);
        db.customerDao().insert((customer));
    }


    private static void populateWithTestData(AppDatabase db) {
        db.orderDao().deleteAll();

        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", 1);
        addOrder(db, 130, "02.01.2020", "23.03.2020", 3,"Delivered", 2);
        addOrder(db, 49.99, "14.02.2020", "23.03.2020", 1,"Delivered",3);
        addOrder(db, 75.60, "12.03.2020", "23.03.2020", 4,"Delivered", 4);


        db.itemDao().deleteAll();

        addItem(db, "Acer Aspire 5", "d", 599, 3, 2, 1);
        addItem(db, "Apple IMac", "d", 1499, 1, 2, 2);
        addItem(db, "Apple MacBook Air", "d", 1099, 2, 2, 3);
        addItem(db, "Asus R543Ma", "d", 299,3, 2, 4);
        addItem(db, "HP Pavillon 15", "d", 899, 3, 2, 5);

        addCategory(db, "Audio");
        addCategory(db, "Camera");
        addCategory(db, "Computer");
        addCategory(db, "Gaming");
        addCategory(db, "Smartphone");
        addCategory(db, "TV");

        addImage(db, "C:\\Users\\Gaby\\Desktop\\Android\\Projet\\Images Mockups\\Computers\\ACER Aspire 5 599 2.png");
        addImage(db, "C:\\Users\\Gaby\\Desktop\\Android\\Projet\\Images Mockups\\Computers\\APPLE iMac 1499.png");
        addImage(db, "C:\\Users\\Gaby\\Desktop\\Android\\Projet\\Images Mockups\\Computers\\APPLE MacBook Air 1099.png");
        addImage(db, "C:\\Users\\Gaby\\Desktop\\Android\\Projet\\Images Mockups\\Computers\\ASUS R543MA-DM661T 299-95.PNG");
        addImage(db, "C:\\Users\\Gaby\\Desktop\\Android\\Projet\\Images Mockups\\Computers\\HP Pavilion 15 899.PNG");

        addCustomer(db, "Marc", "Morand", "Avenue de la Gare 54", "Martigny", 1920, "0785412654", "marc.morand@gmail.com", 1);
        addCustomer(db, "Louise", "Vouilloz", "Chemin de l'Eglise 5", "Sion", 1950, "02789542632", "louise.vouilloz@hotmail.com", 2);
        addCustomer(db, "Jean-Pierre", "Grange", "Route de la Poste 18", "Sierre", 3960, "07963145875", "jean-pierre.grange@gmail.com", 3);
        addCustomer(db, "Marinette", "Michaud", "Rue de la rivière 47", "Martigny", 1920, "07741258741", "marinette.michaud@hotmail.com", 4);



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