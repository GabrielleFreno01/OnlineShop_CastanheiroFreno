package com.android.onlineshop_castanheirofreno.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class OrderEntity implements Comparable {

    private String idOrder;

    private double price;

    private String creationDate;

    private String idItem;

    private String owner;

    private String status;

    private String deliveryDate;

    private String idCategory;

    public OrderEntity() {
    }

    public OrderEntity(double price, String creationDate, String deliveryDate, String idItem, String idCategory, String status, String owner) {
        this.price = price;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.idItem = idItem;
        this.status = status;
        this.owner = owner;
        this.idCategory = idCategory;

    }
    @Exclude
    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String id) {
        this.idOrder = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Exclude
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return idOrder + " " + price;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("price", price);
        result.put("creationDate", creationDate);
        result.put("status", status);
        result.put("deliveryDate", deliveryDate);
        result.put("idCategory", idCategory);
        result.put("idItem", idItem);
        return result;
    }


}
