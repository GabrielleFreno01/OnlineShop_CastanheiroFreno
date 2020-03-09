package com.android.onlineshop_castanheirofreno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchases_confirmation);
    }


    public void goShopping (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}