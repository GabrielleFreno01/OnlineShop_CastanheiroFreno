
package com.android.onlineshop_castanheirofreno.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

//@Entity(tableName = "categories")
public class CategoryEntity {

    //@PrimaryKey(autoGenerate = true)
    private String idCategory;

    //@ColumnInfo(name = "name")
    private String name;

    //@ColumnInfo(name = "tag")
    private String tag;

    // @Ignore
    public CategoryEntity() {
    }

    public CategoryEntity(String name, String tag) {
        this.name = name;
        this.tag = tag;

    }

    //getters & setters
    @Exclude
    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String id) {
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

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name" , name);

        return result;
    }
}

