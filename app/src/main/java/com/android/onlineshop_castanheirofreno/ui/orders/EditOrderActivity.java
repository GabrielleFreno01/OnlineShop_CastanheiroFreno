package com.android.onlineshop_castanheirofreno.ui.orders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.onlineshop_castanheirofreno.R;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class EditOrderActivity extends BaseActivity {

    private static final String TAG = "EditOrderActivity";

    private OrderEntity order;
    private String owner;
    private boolean isEditMode;
    private Toast toast;
    private EditText etPrice;
    private EditText etDeliver;

    private OrderViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_order, frameLayout);

        navigationView.setCheckedItem(position);


        etPrice = findViewById(R.id.textView_product_price);
        etDeliver = findViewById(R.id.textView_delivery_date);

        Button saveBtn = findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(view -> {
            saveChanges(etDeliver.getText().toString(), Integer.parseInt(etPrice.getText().toString()));
            onBackPressed();
            toast.show();
        });

        //SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_ORDER, 0);
        //String owner = settings.getString(PREFS_ORDER, null);

        OrderViewModel.Factory factory = new OrderViewModel.Factory(getApplication(), owner);
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        viewModel.getOrder().observe(this, OrderEntity -> {
            if (OrderEntity != null) {
                order = OrderEntity;

            }
        });
    }


    private void saveChanges(String deliver_date, int price) {
        if (isEditMode) {
            if(!"".equals(deliver_date)) {

                viewModel.updateOrder(order, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "updateOrder: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "updateOrder: failure", e);
                    }
                });
            }
        } else {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOwner(owner);
            newOrder.setPrice(0.0);
            viewModel.createOrder(newOrder, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createOrder: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "createOrder: failure", e);
                }
            });
        }
    }
}
