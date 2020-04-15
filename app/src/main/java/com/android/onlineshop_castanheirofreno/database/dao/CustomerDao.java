/*package com.android.onlineshop_castanheirofreno.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CustomerWithOrders;

import java.util.List;

//@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customer WHERE email = :id")
    LiveData<CustomerEntity> getById(String id);

    @Query("SELECT * FROM customer WHERE email = :id")
    LiveData<CustomerEntity> getByEmail(String id);

    @Query("SELECT * FROM customer")
    LiveData<List<CustomerEntity>> getAll();

    @Transaction
    @Query("SELECT * FROM customer WHERE email != :owner")
    LiveData<List<CustomerWithOrders>> getOtherClientsWithOrders(String owner);

    @Insert
    String insert(CustomerEntity customer) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CustomerEntity> clients);

    @Update
    void update(CustomerEntity customer);

    @Delete
    void delete(CustomerEntity customer);

    @Query("DELETE FROM customer")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM customer WHERE email != :id")
    LiveData<List<CustomerWithOrders>> getOtherCustomersWithOrders(String id);
}
*/