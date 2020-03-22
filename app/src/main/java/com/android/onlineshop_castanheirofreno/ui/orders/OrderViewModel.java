package com.android.onlineshop_castanheirofreno.ui.orders;

import androidx.lifecycle.ViewModel;

import java.util.Date;

public class OrderViewModel extends ViewModel {

    private int idOrder;
    private double totalPrice;
    private String creationDate;
    private int idItem;
    private String status;
    private String deliveryDate;

    public OrderViewModel(int idOrder, double totalPrice, String creationDate, int idItem, String status, String deliveryDate) {
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}


