package com.android.onlineshop_castanheirofreno.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idCategory;

    @ColumnInfo(name = "name")
    private String name;


    @Ignore
    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.name = name;

    }

    //getters & setters
    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long id) {
            this.idCategory = id;
        }

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }


}