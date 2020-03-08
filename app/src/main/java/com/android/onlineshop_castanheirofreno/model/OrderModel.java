package com.android.onlineshop_castanheirofreno.model;

import java.util.Date;

public class OrderModel {

    private int idOrder;
    private double totalPrice;
    private String creationDate;
    private int idItem;
    private String status;
    private Date deliveryDate;

    public OrderModel(int idOrder, double totalPrice, String creationDate, int idItem, String status, Date deliveryDate) {
        this.idOrder = idOrder;
        this.totalPrice = totalPrice;
        this.creationDate = creationDate;
        this.idItem = idItem;
        this.status = status;
        this.deliveryDate = deliveryDate;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
