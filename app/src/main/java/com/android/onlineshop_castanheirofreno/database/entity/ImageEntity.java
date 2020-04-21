package com.android.onlineshop_castanheirofreno.database.entity;

import com.google.firebase.database.Exclude;

public class ImageEntity {

    private String idImage;

    private String lien;


    public ImageEntity() {
    }

    public ImageEntity(String lien) {
        this.lien = lien;

    }

    //getters & setters
    @Exclude
    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String id) {
        this.idImage = id;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }


}