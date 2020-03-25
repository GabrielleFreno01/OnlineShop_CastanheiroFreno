package com.android.onlineshop_castanheirofreno.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idImage;

    @ColumnInfo(name = "lien")
    private String lien;


    @Ignore
    public ImageEntity() {
    }

    public ImageEntity(String lien) {
        this.lien = lien;

    }

    //getters & setters
    public Long getIdImage() {
        return idImage;
    }

    public void setIdImage(Long id) {
        this.idImage = id;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }


}