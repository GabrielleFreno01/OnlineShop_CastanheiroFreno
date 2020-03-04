package com.android.onlineshop_castanheirofreno.model;



public class CategoryListModel {

    private String categoryName;
    private String tag;


    public CategoryListModel(String categoryName, String tag){
        this.categoryName = categoryName;
        this.tag = tag ;

    }

    public String getCategoryName(){
        return categoryName;
    }

    public String getTag(){
        return tag;
    }
}
