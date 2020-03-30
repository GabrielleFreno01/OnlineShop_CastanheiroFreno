package com.android.onlineshop_castanheirofreno.ui.orders;

import android.content.Intent;
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

import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;

import java.text.NumberFormat;

public class OrderDetailActivity extends BaseActivity {

    private static final String TAG = "OrderDetailActivity";

    private static final int EDIT_ORDER = 1;

    private OrderWithItem orderWithItem;

    private TextView tvOrderId;
    private TextView tvOrderStatus;
    private TextView tvProductId;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvOrderDate;
    private TextView tvDeliveryDate;

    private NumberFormat defaultFormat;

    private OrderViewModel viewModel;

    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_order_details, frameLayout);

        navigationView.setCheckedItem(position);

        orderId = getIntent().getLongExtra("orderId", 0L);

        initiateView();

        OrderViewModel.Factory factory = new OrderViewModel.Factory(
                getApplication(), orderId);
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        viewModel.getOrderWithItem().observe(this, orderEntity -> {
            if (orderEntity != null) {
                orderWithItem = orderEntity;
                updateContent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, EDIT_ORDER, Menu.NONE, getString(R.string.title_activity_edit_order))
                .setIcon(R.drawable.ic_edit_blue_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, EditOrderActivity.class);
        intent.putExtra("orderId", orderId);
        startActivity(intent);
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        tvOrderId = findViewById(R.id.textview_id_order);
        defaultFormat = NumberFormat.getCurrencyInstance();

        tvOrderStatus = findViewById(R.id.textView_order_status);

        tvProductId = findViewById(R.id.textView_product_id);

        tvProductName = findViewById(R.id.textView_product_name);

        tvProductPrice = findViewById(R.id.textView_product_price);

        tvOrderDate = findViewById(R.id.textView_order_date);

        tvDeliveryDate = findViewById(R.id.textView_delivery_date);

    }

    private void updateContent() {
        if (orderWithItem != null) {
            tvOrderId.setText(String.valueOf(orderWithItem.order.getIdOrder()));
            tvOrderStatus.setText(orderWithItem.order.getStatus());
            tvProductId.setText(String.valueOf(orderWithItem.order.getIdItem()));
            tvProductName.setText(orderWithItem.item.getName());
            tvProductPrice.setText(defaultFormat.format(orderWithItem.order.getPrice()));
            tvOrderDate.setText(orderWithItem.order.getCreationDate());
            tvDeliveryDate.setText(orderWithItem.order.getDeliveryDate());

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
