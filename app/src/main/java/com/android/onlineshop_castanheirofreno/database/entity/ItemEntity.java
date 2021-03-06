package com.android.onlineshop_castanheirofreno.database.entity;

import android.widget.ImageButton;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "items", foreignKeys = {@ForeignKey(entity = CategoryEntity.class, parentColumns = "idCategory", childColumns = "idCategory")},
        //@ForeignKey(entity = ImageEntity.class, parentColumns = "idImage", childColumns = "idImage")},
        indices = {
                @Index(value = {"idCategory"}),
                //@Index( value = {"idImage"})
        }
)

public class ItemEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idItem;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "idCategory")
    private long idCategory;

    /*@ColumnInfo(name = "idImage")
    private long idImage;*/


    @Ignore
    public ItemEntity() {
    }

    public ItemEntity(String name, String description, double price, long idCategory) {//, long idImage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        //this.idImage = idImage;
    }

    //getters & setters


    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long id) {
        this.idItem = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    //public long getIdImage() { return idImage; }

    //public void setIdImage(long image) { this.idImage = image; }


}