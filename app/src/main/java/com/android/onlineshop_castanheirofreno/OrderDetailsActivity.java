package com.android.onlineshop_castanheirofreno;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.onlineshop_castanheirofreno.ui.orders.OrderViewModel;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView idOrder_tv;
    TextView orderStatus_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);


        Intent intent =  getIntent();

        int idOrder = intent.getIntExtra("ID_ORDER", 0);

        //Replace by a function that gets the order with the idOrder
        OrderViewModel order = new OrderViewModel(idOrder, 599.00, "03.03.2020", 1, "Delivered", null);

        idOrder_tv = findViewById(R.id.textview_id_order);
        idOrder_tv.setText("N°"+Integer.toString(order.getIdOrder()));

        orderStatus_tv = findViewById(R.id.textView_order_status);
        orderStatus_tv.setText(order.getStatus());

    }
}