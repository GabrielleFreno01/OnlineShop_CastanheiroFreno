package com.android.onlineshop_castanheirofreno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProductDescriptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);

        //ImageView productImage = (ImageView) this.findViewById(R.id.imageView);
    }

    public void seeShoppingCart (View view){
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
