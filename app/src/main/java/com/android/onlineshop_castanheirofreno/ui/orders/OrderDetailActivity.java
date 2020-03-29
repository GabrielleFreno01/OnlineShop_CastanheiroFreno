package com.android.onlineshop_castanheirofreno.ui.orders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.onlineshop_castanheirofreno.R;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;

import java.text.NumberFormat;

public class OrderDetailActivity extends BaseActivity {

    private static final String TAG = "OrderDetailActivity";

    private static final int EDIT_ORDER = 1;

    private OrderEntity order;

    private TextView tvOrderId;
    private TextView tvOrderStatus;
    private TextView tvProductId;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvOrderDate;
    private TextView tvDeliveryDate;

    private NumberFormat defaultFormat;

    private OrderViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_order_details, frameLayout);

        navigationView.setCheckedItem(position);

        initiateView();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_ORDER, 0);
        String owner = settings.getString(PREFS_ORDER, null);

        OrderViewModel.Factory factory = new OrderViewModel.Factory(getApplication(), owner);
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        viewModel.getOrder().observe(this, OrderEntity -> {
            if (OrderEntity != null) {
                order = OrderEntity;
                updateContent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, EDIT_ORDER, Menu.NONE, getString(R.string.title_activity_edit_order))
                .setIcon(R.drawable.ic_edit)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, EditOrderActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        tvOrderId = findViewById(R.id.textview_id_order);
        defaultFormat = NumberFormat.getCurrencyInstance();

        tvOrderStatus = findViewById(R.id.textView_order_status);
        defaultFormat = NumberFormat.getCurrencyInstance();

        tvProductId = findViewById(R.id.textView_product_id);
        defaultFormat = NumberFormat.getCurrencyInstance();

        tvProductName = findViewById(R.id.textView_product_name);
        defaultFormat = NumberFormat.getCurrencyInstance();

        tvProductPrice = findViewById(R.id.textView_product_price);
        defaultFormat = NumberFormat.getCurrencyInstance();

        tvOrderDate = findViewById(R.id.textView_order_date);
        defaultFormat = NumberFormat.getCurrencyInstance();

        tvDeliveryDate = findViewById(R.id.textView_delivery_date);
        defaultFormat = NumberFormat.getCurrencyInstance();

        Button btn_deliver = findViewById(R.id.button_deliver);
        btn_deliver.setOnClickListener(view -> generateDialog(R.string.action_deliver));

        Button btn_change = findViewById(R.id.button_change_product);
        btn_change.setOnClickListener(view -> generateDialog(R.string.action_change));

        Button btn_cancel = findViewById(R.id.button_cancel);
        btn_cancel.setOnClickListener(view -> generateDialog(R.string.action_cancel));
    }

    private void updateContent() {
        if (order != null) {
            tvOrderId.setText(defaultFormat.format(order.getIdOrder()));
            tvOrderStatus.setText(defaultFormat.format(order.getStatus()));
            tvProductId.setText(defaultFormat.format(order.getIdItem()));
            //tvProductName.setText(defaultFormat.format(order.getIdOrder()));
            tvProductPrice.setText(defaultFormat.format(order.getPrice()));
            tvOrderDate.setText(defaultFormat.format(order.getCreationDate()));
            tvDeliveryDate.setText(defaultFormat.format(order.getDeliveryDate()));

        }
    }

    private void generateDialog(final int action) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.order_action, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(action));
        alertDialog.setCancelable(false);



    }
}
