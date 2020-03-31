
package com.android.onlineshop_castanheirofreno.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private long idCategory;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "tag")
    private String tag;

    @Ignore
    public CategoryEntity() {
    }

    public CategoryEntity(String name, String tag) {
        this.name = name;
        this.tag = tag;

    }

    //getters & setters
    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long id) {
        this.idCategory = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String toString(){
        return getName();
    }
}