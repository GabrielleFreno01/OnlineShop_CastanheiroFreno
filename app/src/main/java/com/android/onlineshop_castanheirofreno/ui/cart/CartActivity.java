package com.android.onlineshop_castanheirofreno.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.confirmation.ConfirmationActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;


public class CartActivity extends BaseActivity {

    private CartViewModel cartViewModel;
    private Button buy;
    private Button goShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("My Cart");
        navigationView.setCheckedItem(position);
        getLayoutInflater().inflate(R.layout.activity_cart, frameLayout);

        buy = (Button) findViewById(R.id.btn_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeConfirmation(v);
            }
        });

        goShopping = (Button) findViewById(R.id.btn_go);
        goShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShopping(v);
            }
        });

    }

    public void seeConfirmation (View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
        onBackPressed();
    }

    public void goShopping (View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        onBackPressed();
    }
}
