package com.android.onlineshop_castanheirofreno.database.entity;

import com.google.firebase.database.Exclude;

//@Entity(tableName = "images")
public class ImageEntity {

    //@PrimaryKey(autoGenerate = true)
    private Long idImage;

    //@ColumnInfo(name = "lien")
    private String lien;


    //@Ignore
    public ImageEntity() {
    }

    public ImageEntity(String lien) {
        this.lien = lien;

    }

    //getters & setters
    @Exclude
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