package com.android.onlineshop_castanheirofreno.ui.orders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onlineshop_castanheirofreno.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.adapter.OrdersAdapter;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;
import com.android.onlineshop_castanheirofreno.viewmodel.order.OrderListViewModel;

import java.util.ArrayList;
import java.util.List;


public class OrdersActivity extends BaseActivity {

    private static final String TAG = "OrdersActivity";

    private List<OrderWithItem> orders;
    private OrdersAdapter adapter;
    private OrderListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_order_list, frameLayout);

        setTitle("Orders");
        navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.OrderRecyclerView);


        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_USER, 0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);

        //New list of orders
        orders = new ArrayList<>();

        adapter = new OrdersAdapter(this, orders, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //get the details
                Intent intent = new Intent(OrdersActivity.this, OrderDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("orderId", orders.get(position).order.getIdOrder());
                startActivity(intent);

            }


            //Delete an order
            @Override
            public void onItemLongClick(View v, int position) {
                createDeleteDialog(position);
            }
        });

        OrderListViewModel.Factory factory = new OrderListViewModel.Factory(
                getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(OrderListViewModel.class);
        viewModel.getOwnOrders().observe(this, ordersWithItem -> {
            if (ordersWithItem != null) {
                orders = ordersWithItem;
                adapter.setData(orders);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        finish();
        return super.onNavigationItemSelected(item);
    }

    private void createDeleteDialog(final int position) {
        final OrderWithItem orderWithItem = orders.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete order ?");
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText(String.format("Do you to delete the order " + orderWithItem.order.getIdOrder() + " ?"));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_accept), (dialog, which) -> {
            Toast toast = Toast.makeText(this, "Order deleted", Toast.LENGTH_LONG);
            viewModel.deleteOrder(orderWithItem, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "deleteOrder: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "deleteOrder: failure", e);
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        /*if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }*/
        //super.onBackPressed();
        startActivity(new Intent(this, CategoryActivity.class));
    }
}
