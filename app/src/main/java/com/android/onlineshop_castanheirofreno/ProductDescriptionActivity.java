package com.android.onlineshop_castanheirofreno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



public class ProductDescriptionActivity extends AppCompatActivity {

    private Button btn_add_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);

        btn_add_cart = (Button) findViewById(R.id.btn_add_cart);


        /*btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ProductDescriptionActivity.this,
                        CartFragment.class
                );
            }
        });*/

        //ImageView productImage = (ImageView) this.findViewById(R.id.imageView);
    }

    /*public void seeShoppingCart (View view){
        Intent intent = new Intent(this, CartFragment.class);
        startActivity(intent);
    }*/
}
