package com.android.onlineshop_castanheirofreno;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.onlineshop_castanheirofreno.model.ItemModel;
import com.android.onlineshop_castanheirofreno.ui.orders.OrderViewModel;

import java.util.Date;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView idOrder_tv;
    private TextView orderStatus_tv;
    private TextView orderDate_tv;
    private TextView productName_tv;
    private TextView productPrice_tv;
    private TextView productId_tv;
    private TextView deliveryDate_tv;

    private Button cancelButton;
    private Button deliverButton;
    private Button changeProductButton;

    private TableRow cancelButton_tableRow;
    private TableRow deliveryDate_tableRow;
    private TableRow deliverButton_tableRow;
    private TableRow changeProductButton_tableRow;

    private String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);


        Intent intent =  getIntent();

        int idOrder = intent.getIntExtra("ID_ORDER", 0);

        //Replace by a function that gets the order with the idOrder
        OrderViewModel order = new OrderViewModel(idOrder, 599.00, "03.03.2020", 1, "In progress", "15.03.2020");

        //Replace by a function that gets the item with the idItem
        ItemModel item = new ItemModel(1, R.drawable.apple_imac_1499, "Apple iMac", 1499.00);

        //Filling informations about the order into the layout
        idOrder_tv = findViewById(R.id.textview_id_order);
        idOrder_tv.setText(Integer.toString(order.getIdOrder()));

        orderStatus_tv = findViewById(R.id.textView_order_status);
        orderStatus = order.getStatus();
        orderStatus_tv.setText(orderStatus);

        productId_tv = findViewById(R.id.textView_product_id);
        productId_tv.setText(Integer.toString(order.getIdItem()));

        productName_tv = findViewById(R.id.textView_product_name);
        productName_tv.setText(item.getProductName());

        productPrice_tv = findViewById(R.id.textView_product_price);
        productPrice_tv.setText("CHF "+item.getPrice());

        orderDate_tv = findViewById(R.id.textView_order_date);
        orderDate_tv.setText(order.getCreationDate());


        //Changing layout according to status
        deliveryDate_tv = findViewById(R.id.textView_delivery_date);
        deliveryDate_tableRow  = findViewById(R.id.tablerow_delivery_date);
        cancelButton_tableRow = findViewById(R.id.tablerow_cancel_button);
        deliverButton_tableRow = findViewById(R.id.tablerow_deliver_button);
        changeProductButton_tableRow = findViewById(R.id.tablerow_changeProduct_button);

        if(orderStatus == "Delivered") {
            deliveryDate_tv.setText(order.getDeliveryDate());
            orderStatus_tv.setTextColor(getResources().getColor(R.color.validate));
            deliverButton_tableRow.setVisibility(View.GONE);
            cancelButton_tableRow.setVisibility(View.GONE);
            changeProductButton_tableRow.setVisibility(View.GONE);
        }
        if(orderStatus == "Canceled") {
            orderStatus_tv.setTextColor(getResources().getColor(R.color.cancel));
            deliveryDate_tableRow.setVisibility(View.GONE);
            deliverButton_tableRow.setVisibility(View.GONE);
            cancelButton_tableRow.setVisibility(View.GONE);
            changeProductButton_tableRow.setVisibility(View.GONE);
        }
        if(orderStatus == "In progress") {
            orderStatus_tv.setTextColor(getResources().getColor(R.color.inprogress));
            deliveryDate_tableRow.setVisibility(View.GONE);
        }

        //Setting the listeners to the buttons
        deliverButton = findViewById(R.id.button_deliver);
        deliverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverOrder();
            }
        });

        cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });

        changeProductButton = findViewById(R.id.button_change_product);
        changeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProduct();
            }
        });



    }

    private void deliverOrder(){
        //TODO Change the status of the order

    }private void changeProduct(){
        //TODO Change the product of the order

    }
    private void cancelOrder(){
        //TODO Change the status of the order

    }
}