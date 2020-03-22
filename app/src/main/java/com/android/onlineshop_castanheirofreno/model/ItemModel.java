package com.android.onlineshop_castanheirofreno.model;



public class ItemModel {
    private int image;
    private String productName;
    private double price;
    private int idItem;
    private String description;
    private int quantity;

    public ItemModel(int idItem, int image, String productName, double price, int quantity, String description) {
        this.idItem = idItem;
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }
    public ItemModel(int idItem, int image, String productName, double price) {
        this.idItem = idItem;
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.description = "Description";
        this.quantity = 20;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getIdItem() {
        return idItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
