package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.android.onlineshop_castanheirofreno.R;
import androidx.annotation.NonNull;

import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.cart.CartActivity;



public class ItemDescriptionActivity extends BaseActivity {

    private Button btn_add_cart;
    private ImageButton btn_modify_product;

    private ItemModel itemModel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_product_description, frameLayout);


        navigationView.setCheckedItem(position);
        //Replace by a function that gets the item with the idItem
        itemModel = new ItemModel(2, R.drawable.apple_imac_1499, "Apple iMac", 1499.00);

        btn_add_cart = findViewById(R.id.btn_add_cart);
        btn_modify_product = findViewById(R.id.btn_modify_product);



        btn_modify_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { modifyProduct();}
        });

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        //ImageView productImage = (ImageView) this.findViewById(R.id.imageView);
    }

    public void addToCart() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void modifyProduct() {
        Intent intent = new Intent(this, AddItemActivity.class);
        //intent.putExtra("ID_ITEM", itemModel.getIdItem());
        startActivity(intent);
    }
}




