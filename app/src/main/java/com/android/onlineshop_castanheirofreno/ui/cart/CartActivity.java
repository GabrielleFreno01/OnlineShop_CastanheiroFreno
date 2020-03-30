package com.android.onlineshop_castanheirofreno.ui.cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.async.item.CreateItem;
import com.android.onlineshop_castanheirofreno.database.async.order.CreateOrder;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.confirmation.ConfirmationActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.ui.item.ItemListActivity;
import com.android.onlineshop_castanheirofreno.ui.item.ItemViewModel;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.text.DateFormat;
import java.util.Date;


public class CartActivity extends BaseActivity {

    private CartViewModel cartViewModel;
    private TextView etproductName;
    private TextView etproductPrice;
    private Button buy;
    private Button goShopping;

    private Toast toast;

    ItemViewModel viewModel;

    private Date now = new Date();

    private DateFormat dateformatter;

    private String formattedDate ;

    private static final String TAG = "AddOrder";


    ItemEntity item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("My Cart");
        navigationView.setCheckedItem(position);
        getLayoutInflater().inflate(R.layout.activity_cart, frameLayout);

        initiateView();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        long itemid = settings.getLong(ItemListActivity.PREFS_ITEM, 0);

        SharedPreferences settingsUser = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settingsUser.getString(PREFS_USER, null);

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemid);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                item = itemEntity;
                updateContent();
            }

        });

        buy = (Button) findViewById(R.id.btn_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateformatter = DateFormat.getDateInstance(DateFormat.SHORT);
                formattedDate = dateformatter.format(now);

                saveChanges(
                        (Integer.parseInt(etproductPrice.getText().toString())),
                        formattedDate,
                        null,
                        itemid,
                        "In progress",
                        user
                );
                seeConfirmation(v);
                SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
                editor.remove(ItemListActivity.PREFS_ITEM);
                editor.apply();
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


    private void saveChanges(double price, String creationDate,String deliveryDate, long idItem, String status, String owner){

        OrderEntity newOrder = new OrderEntity(price, creationDate, deliveryDate, idItem, status,owner);

        new CreateOrder(getApplication(), new OnAsyncEventListener() {

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
        }).execute(newOrder);

    }

    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, "Order created", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void updateContent() {
        if (item != null) {
            etproductName.setText(item.getName());
            etproductPrice.setText(String.valueOf(item.getPrice()));
        }
    }

    private void initiateView() {
        etproductName = findViewById(R.id.new_item_name);
        etproductPrice = findViewById(R.id.new_item_price);
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
