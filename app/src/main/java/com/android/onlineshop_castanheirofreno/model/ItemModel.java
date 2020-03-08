package com.android.onlineshop_castanheirofreno.model;

public class ItemModel {
    private int image;
    private String productName;
    private String price;

    public ItemModel(int image, String productName, String price) {
        this.image = image;
        this.productName = productName;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
