package com.android.onlineshop_castanheirofreno.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM items WHERE idItem = :id")
    LiveData<ItemEntity> getById(long id);

    @Query("SELECT * FROM items")
    LiveData<List<ItemEntity>> getAll();

    @Query("SELECT * FROM items WHERE idCategory =:id")
    LiveData<List<ItemEntity>> getItemsByCategory(long id);

    @Insert
    long insert(ItemEntity item) throws SQLiteConstraintException;

    @Update
    void update(ItemEntity item);

    @Delete
    void delete(ItemEntity item);

    @Query("DELETE FROM items")
    void deleteAll();
}
