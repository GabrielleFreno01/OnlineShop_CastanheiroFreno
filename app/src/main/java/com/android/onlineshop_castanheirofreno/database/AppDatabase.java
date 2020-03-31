package com.android.onlineshop_castanheirofreno.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.android.onlineshop_castanheirofreno.database.dao.CategoryDao;
import com.android.onlineshop_castanheirofreno.database.dao.CustomerDao;
import com.android.onlineshop_castanheirofreno.database.dao.ImageDao;
import com.android.onlineshop_castanheirofreno.database.dao.ItemDao;
import com.android.onlineshop_castanheirofreno.database.dao.OrderDao;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ImageEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {OrderEntity.class, CustomerEntity.class, ItemEntity.class, ImageEntity.class, CategoryEntity.class}, version = 11, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    private static final String DATABASE_NAME = "Technospot Database";

    public abstract OrderDao orderDao();

    public abstract CustomerDao customerDao();

    public abstract ItemDao itemDao();

    public abstract CategoryDao categoryDao();

    public abstract ImageDao imageDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            initializeData(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).fallbackToDestructiveMigration()
                .build();
    }

    public static void initializeData(final AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                Log.i(TAG, "Wipe database.");
                database.orderDao().deleteAll();
                database.itemDao().deleteAll();
                database.customerDao().deleteAll();
                database.categoryDao().deleteAll();
                database.imageDao().deleteAll();

                database.clearAllTables();

                DatabaseInitializer.populateDatabase(database);
            });
        });
    }


    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}