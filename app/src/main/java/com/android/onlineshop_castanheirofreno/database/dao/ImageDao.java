/*package com.android.onlineshop_castanheirofreno.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.onlineshop_castanheirofreno.database.entity.ImageEntity;

import java.util.List;

//@Dao
public interface ImageDao {

    @Query("SELECT * FROM images WHERE idImage = :id")
    LiveData<ImageEntity> getById(Long id);

    @Query("SELECT * FROM images")
    LiveData<List<ImageEntity>> getAll();

    @Query("DELETE FROM images")
    void deleteAll();

    @Insert
    long insert(ImageEntity image) throws SQLiteConstraintException;
}
*/