package com.android.onlineshop_castanheirofreno.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}