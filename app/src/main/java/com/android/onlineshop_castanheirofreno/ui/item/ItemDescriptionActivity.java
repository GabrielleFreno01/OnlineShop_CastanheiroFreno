package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.android.onlineshop_castanheirofreno.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.cart.CartActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;


public class ItemDescriptionActivity extends BaseActivity {

    private Button btn_add_cart;

    private static final int EDIT_ORDER = 1;
    private static final int DELETE_ORDER = 2;

    ItemEntity itemEntity;

    private ItemModel itemModel;

    private ItemViewModel viewModel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_product_description, frameLayout);


        navigationView.setCheckedItem(position);
        //Replace by a function that gets the item with the idItem
        itemModel = new ItemModel(2, R.drawable.apple_imac_1499, "Apple iMac", 1499.00);

        btn_add_cart = findViewById(R.id.btn_add_cart);


        //ImageView productImage = (ImageView) this.findViewById(R.id.imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, EDIT_ORDER, Menu.NONE, getString(R.string.action_edit))
                .setIcon(R.drawable.ic_edit)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, DELETE_ORDER, Menu.NONE, getString(R.string.action_delete))
                .setIcon(R.drawable.ic_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_ORDER) {
            item.setIcon(R.drawable.ic_edit);
            Intent intent = new Intent(this,EditItemActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == DELETE_ORDER) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteItem(itemEntity, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        logout();
                    }

                    @Override
                    public void onFailure(Exception e) {}
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addToCart() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void modifyProduct() {
        Intent intent = new Intent(this, EditItemActivity.class);
        //intent.putExtra("ID_ITEM", itemModel.getIdItem());
        startActivity(intent);
    }
}




