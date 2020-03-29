package com.android.onlineshop_castanheirofreno.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CustomerWithOrders;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;

import java.util.List;

@Dao
public abstract class OrderDao {

    @Query("SELECT * FROM orders WHERE idOrder = :id")
    public abstract LiveData<OrderEntity> getById(Long id);

    @Query("SELECT * FROM orders")
    public abstract LiveData<List<OrderEntity>> getAll();

    @Query("SELECT * FROM orders WHERE owner=:owner")
    public abstract LiveData<List<OrderEntity>> getOwned(String owner);

    @Transaction
    @Query("SELECT * FROM orders WHERE owner=:owner")
    public abstract LiveData<List<OrderWithItem>> getOwnedOrdersWithItem(String owner);

    @Insert
    public abstract long insert(OrderEntity order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<OrderEntity> orders);

    @Update
    public abstract void update(OrderEntity order);

    @Delete
    public abstract void delete(OrderEntity order);

    @Query("DELETE FROM orders")
    public abstract void deleteAll();



    @Transaction
    public void transaction(OrderEntity sender, OrderEntity recipient) {
        update(sender);
        update(recipient);
    }
}
