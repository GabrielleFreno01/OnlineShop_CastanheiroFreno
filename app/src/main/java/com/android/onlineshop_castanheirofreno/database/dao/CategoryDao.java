package com.android.onlineshop_castanheirofreno.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;

import java.util.List;
@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories WHERE idCategory = :id")
    LiveData<CategoryEntity> getById(Long id);

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAll();

    @Insert
    void insert(CategoryEntity category) throws SQLiteConstraintException;



}
