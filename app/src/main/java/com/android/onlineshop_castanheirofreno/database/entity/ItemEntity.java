package com.android.onlineshop_castanheirofreno.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;



public class ItemEntity {

    private String idItem;
    private String name;
    private String description;
    private double price;
    private String idCategory;
    private String image;

    public ItemEntity() {
    }

    public ItemEntity(String name, String description, double price, String idCategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        this.image=image;
    }

    //getters & setters

    @Exclude
    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String id) {
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

    @Exclude
    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name" , name);
        result.put("description" , description);
        result.put("price" , price);
        return result;
    }

}