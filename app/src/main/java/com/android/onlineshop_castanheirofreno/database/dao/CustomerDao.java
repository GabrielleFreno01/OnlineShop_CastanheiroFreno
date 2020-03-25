package com.android.onlineshop_castanheirofreno.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;

import java.util.List;
@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customers WHERE idCustomer= :id")
    LiveData<CustomerEntity> getById(Long id);

    @Query("SELECT * FROM customers")
    LiveData<List<CustomerEntity>> getAll();

    @Insert
    void insert(CustomerEntity customer) throws SQLiteConstraintException;
}
