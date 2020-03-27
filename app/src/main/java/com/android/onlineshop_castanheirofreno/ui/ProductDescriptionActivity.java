package com.android.onlineshop_castanheirofreno.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.model.ItemModel;



public class ProductDescriptionActivity extends AppCompatActivity {

    private Button btn_add_cart;
    private ImageButton btn_modify_product;

    private ItemModel itemModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.product_description, container, false);

        return root;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        //Replace by a function that gets the item with the idItem
        itemModel = new ItemModel(2, R.drawable.apple_imac_1499, "Apple iMac", 1499.00);

        btn_add_cart = findViewById(R.id.btn_add_cart);
        btn_modify_product = findViewById(R.id.btn_modify_product);



        btn_modify_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyProduct();
            }
        });

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //ImageView productImage = (ImageView) this.findViewById(R.id.imageView);
    }
    public void modifyProduct() {
        Intent intent = new Intent(this, ModifyProductActivity.class);
        intent.putExtra("ID_ITEM", itemModel.getIdItem());
        startActivity(intent);
    }
    /*public void seeShoppingCart(){
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }*/
}


