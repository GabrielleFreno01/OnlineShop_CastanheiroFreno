package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.cart.CartActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicBoolean;


public class ItemDescriptionActivity extends BaseActivity {

    private Button btn_add_cart;

    private static final String TAG = "ItemDescriptionActivity";

    private static final int EDIT_ORDER = 1;
    private static final int DELETE_ORDER = 2;

    private static final int REQUEST_EXIT = 1;

    private String catId;

    private TextView tvProductName;
    private TextView tvPriceProduct;
    private TextView tvDescription;
    private Toast toast;

    private ItemEntity item;


    private ItemViewModel viewModel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_product_description, frameLayout);

        navigationView.setCheckedItem(position);


        initiateView();

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        catId = intent.getStringExtra("idCategory");


        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemId, catId);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                item = itemEntity;
                updateContent();

            }


        });

        btn_add_cart = findViewById(R.id.btn_add_cart);

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();

            }
        });


    }

    private void updateContent() {
        if (item != null) {
            NumberFormat defaultFormat = new DecimalFormat("#0.00");
            tvProductName.setText(item.getName());
            tvPriceProduct.setText("CHF " + defaultFormat.format(item.getPrice()));
            tvDescription.setText(item.getDescription());
        }

    }

    private void initiateView() {
        tvProductName = findViewById(R.id.new_item_name);
        tvPriceProduct = findViewById(R.id.new_item_price);
        tvDescription = findViewById(R.id.productDescription);
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
    public boolean onOptionsItemSelected(MenuItem itemmenu) {
        if (itemmenu.getItemId() == EDIT_ORDER) {
            itemmenu.setIcon(R.drawable.ic_edit);

            Intent intent = new Intent(ItemDescriptionActivity.this, EditItemActivity.class);
            intent.putExtra("itemId", item.getIdItem());
            intent.putExtra("idCategory", item.getIdCategory());
            startActivityForResult(intent, REQUEST_EXIT);
        }
        if (itemmenu.getItemId() == DELETE_ORDER) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteItem(item, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Delete item: success");
                        setResponse(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, e.toString());
                        setResponse(false);
                    }
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();

        }
        return super.onOptionsItemSelected(itemmenu);
    }

    public void addToCart() {
        Intent intent = new Intent(this, CartActivity.class);
        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_ITEM, 0).edit();
        editor.remove(BaseActivity.PREFS_ITEM);
        editor.putString(BaseActivity.PREFS_ITEM, item.getIdItem());
        editor.apply();
        SharedPreferences.Editor editor2 = getSharedPreferences(BaseActivity.PREFS_CATEGORYID, 0).edit();
        editor2.remove(BaseActivity.PREFS_CATEGORYID);
        editor2.putString(BaseActivity.PREFS_CATEGORYID, item.getIdCategory());
        editor2.apply();
        intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_NO_ANIMATION
        );
        startActivity(intent);

        super.onNavigationItemSelected(options.findItem(R.id.nav_cart));
    }

    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, "Item deleted", Toast.LENGTH_LONG);
            toast.show();
            onBackPressed();
        }else {
            toast = Toast.makeText(this, "Item not deleted, please contact the administrator", Toast.LENGTH_LONG);
            toast.show();
            onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

}




