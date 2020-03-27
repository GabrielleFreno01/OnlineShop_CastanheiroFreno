package com.android.onlineshop_castanheirofreno.ui.category;

import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {


    private String categoryName;
    private String tag;

    public CategoryViewModel(String categoryName, String tag) {

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