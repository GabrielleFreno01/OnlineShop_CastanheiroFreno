package com.android.onlineshop_castanheirofreno.ui.cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.confirmation.ConfirmationActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;


public class CartActivity extends BaseActivity {


    private TextView etproductName;
    private TextView etproductPrice;
    private Button buy;
    private Button goShopping;
    private TextView etEmptyCart;
    private ImageView ivEmptyCart;
    private CardView cardView;

    private Toast toast;

    private ItemViewModel viewModel;

    private SimpleDateFormat dateformatter;

    private String formattedDate;

    private static final String TAG = "AddOrder";

    ItemEntity item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create the activity
        super.onCreate(savedInstanceState);
        setTitle("My Cart");
        navigationView.setCheckedItem(position);
        getLayoutInflater().inflate(R.layout.activity_cart, frameLayout);

        initiateView();

        //get the item
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_ITEM, 0);
        String itemId = settings.getString(BaseActivity.PREFS_ITEM,"");

        SharedPreferences settingsUser = getSharedPreferences(BaseActivity.PREFS_USER, 0);
        String user = settingsUser.getString(BaseActivity.PREFS_USER, "");
        System.out.println(user);

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemId, "");
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                item = itemEntity;
                buy.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                etEmptyCart.setVisibility(View.GONE);
                ivEmptyCart.setVisibility(View.GONE);
                updateContent();
            } else {
                buy.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                etEmptyCart.setVisibility(View.VISIBLE);
                ivEmptyCart.setVisibility(View.VISIBLE);
            }


        });


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateformatter = new SimpleDateFormat("dd.MM.yyyy");
                formattedDate = dateformatter.format(System.currentTimeMillis());

                saveChanges((item.getPrice()), formattedDate, null, item.getIdItem(), "In progress", user);
                seeConfirmation(v);
                SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_ITEM, 0).edit();
                editor.remove(BaseActivity.PREFS_ITEM);
                editor.apply();
                recreate();

            }
        });


        goShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShopping(v);
            }
        });

    }


    private void saveChanges(double price, String creationDate, String deliveryDate, String idItem, String status, String owner) {

        OrderEntity newOrder = new OrderEntity(price, creationDate, deliveryDate, idItem, status, owner);

        /*new CreateOrder(getApplication(), new OnAsyncEventListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "createOrder: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createItem: failure", e);
                setResponse(false);
            }
        }).execute(newOrder);*/

    }

    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, "Order created", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void updateContent() {
        if (item != null) {
            NumberFormat defaultFormat = new DecimalFormat("#0.00");
            etproductName.setText(item.getName());
            etproductPrice.setText("CHF " + defaultFormat.format(item.getPrice()));

        }
    }

    private void initiateView() {
        etproductName = findViewById(R.id.new_item_name);
        etproductPrice = findViewById(R.id.new_item_price);
        etEmptyCart = findViewById(R.id.empty_cart_tv);
        ivEmptyCart = findViewById(R.id.empty_cart_image);
        goShopping = findViewById(R.id.btn_go);
        buy = findViewById(R.id.btn_buy);
        cardView = findViewById(R.id.cart_item_cardview);
    }

    public void seeConfirmation(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
        finish();
    }

    public void goShopping(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}
