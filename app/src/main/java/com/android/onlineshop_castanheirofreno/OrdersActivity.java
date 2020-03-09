package com.android.onlineshop_castanheirofreno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.android.onlineshop_castanheirofreno.adapter.ItemsListAdapter;
import com.android.onlineshop_castanheirofreno.adapter.OrdersListAdapter;
import com.android.onlineshop_castanheirofreno.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private List<OrderModel> ordersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list);

        ordersList.add(new OrderModel(1,599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderModel(2,599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderModel(3,599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderModel(4,599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderModel(5,599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderModel(6,599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderModel(7,599.00, "03.03.2020", 1, "In Progress", null));

        ListView listView = findViewById(R.id.listView_orders);

        listView.setAdapter(new OrdersListAdapter(ordersList, this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeOrderDetails(view);
            }
        });
    }

    private void seeOrderDetails(View view) {

    }
}
