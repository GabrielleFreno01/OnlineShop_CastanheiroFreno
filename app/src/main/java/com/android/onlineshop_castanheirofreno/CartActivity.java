package com.android.onlineshop_castanheirofreno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
    }

    public void seeConfirmation (View view){
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }

    public void goShopping (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
