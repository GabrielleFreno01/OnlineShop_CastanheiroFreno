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
public interface OrderDao {

    @Query("SELECT * FROM orders WHERE idOrder = :id")
    public abstract LiveData<OrderEntity> getById(Long id);

    @Transaction
    @Query("SELECT * FROM orders WHERE idOrder = :id")
    public abstract LiveData<OrderWithItem> getOrderWithItem(Long id);

    @Query("SELECT * FROM orders")
    public abstract LiveData<List<OrderEntity>> getAll();

    @Query("SELECT * FROM orders WHERE owner=:owner")
    public abstract LiveData<List<OrderEntity>> getOwned(String owner);

    @Transaction
    @Query("SELECT * FROM orders WHERE owner=:owner")
    public abstract LiveData<List<OrderWithItem>> getOwnedOrdersWithItem(String owner);

    @Insert
    public  long insert(OrderEntity order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  void insertAll(List<OrderEntity> orders);

    @Update
    public  void update(OrderEntity order);

    @Delete
    public  void delete(OrderEntity order);

    @Query("DELETE FROM orders")
    public  void deleteAll();

}
