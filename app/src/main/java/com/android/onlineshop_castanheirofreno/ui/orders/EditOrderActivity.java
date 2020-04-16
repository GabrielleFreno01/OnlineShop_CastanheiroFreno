package com.android.onlineshop_castanheirofreno.ui.orders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.viewmodel.order.OrderViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

public class EditOrderActivity extends BaseActivity {

    private static final String TAG = "EditOrderActivity";

    private OrderWithItem order;
    private String owner;
    private Toast toast;
    private EditText etPrice;
    private TextView tvDeliver;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Spinner spStatus;
    private TableRow trDelivery;
    private TextView tvItemName;
    private TextView tvItemNumber;
    private TextView tvCreationDate;
    private TextView tvOrderId;


    private OrderViewModel viewModel;
    private OrderWithItem orderWithItem;
    private List<ItemEntity> myItemsList;

    private String orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_order, frameLayout);

        navigationView.setCheckedItem(position);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_USER, 0);
        owner = settings.getString(BaseActivity.PREFS_USER, null);


        orderId = getIntent().getStringExtra("orderId");

        initiateView();

        OrderViewModel.Factory factory = new OrderViewModel.Factory(
                getApplication(), orderId);
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        viewModel.getOrderWithItem().observe(this, orderEntity -> {
            if (orderEntity != null) {
                //orderWithItem = orderEntity;
                updateContent();
            }
        });

    }

    private void initiateView() {
        etPrice = findViewById(R.id.textView_product_price);
        tvOrderId = findViewById(R.id.textview_id_order);
        tvDeliver = findViewById(R.id.textView_delivery_date);
        spStatus = findViewById(R.id.spinner_status);
        trDelivery = findViewById(R.id.tablerow_delivery_date);
        tvItemName = findViewById(R.id.textView_product_name);
        tvCreationDate = findViewById(R.id.textView_order_date);
        tvItemNumber = findViewById(R.id.textView_product_id);
        trDelivery.setVisibility(View.GONE);

        tvItemNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialogItemsList();
            }
        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spStatus.getSelectedItem().equals("Delivered")) {
                    trDelivery.setVisibility(View.VISIBLE);
                } else {
                    trDelivery.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvDeliver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditOrderActivity.this,
                        R.style.AppTheme_DatePicker,
                        dateSetListener,
                        year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String monthString = String.valueOf(month);
                String dayString = String.valueOf(dayOfMonth);
                if (month < 10)
                    monthString = "0" + month;

                if (dayOfMonth < 10)
                    dayString = "0" + dayOfMonth;

                String date = dayString + "." + monthString + "." + year;
                tvDeliver.setText(date);
            }
        };

        Button saveBtn = findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(view -> {
            saveChanges();
            onBackPressed();
            toast = Toast.makeText(this, getString(R.string.OrderEdited), Toast.LENGTH_LONG);
            toast.show();
        });


    }

    private void updateContent() {
        if (orderWithItem != null) {

            tvOrderId.setText(String.valueOf(orderId));

            spStatus.setSelection(getIndex(spStatus, orderWithItem.order.getStatus()));

            String deliveryDate = orderWithItem.order.getDeliveryDate();
            if (deliveryDate != "") {
                tvDeliver.setText(deliveryDate);
            }

            tvItemNumber.setText(String.valueOf(orderWithItem.order.getIdItem()));
            tvItemName.setText(orderWithItem.item.getName());
            NumberFormat formatter = new DecimalFormat("#0.00");
            etPrice.setText(formatter.format(orderWithItem.order.getPrice()));
            etPrice.setHint(formatter.format(orderWithItem.order.getPrice()));
            tvCreationDate.setText(orderWithItem.order.getCreationDate());

        }
    }

    private void saveChanges() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        if (String.valueOf(orderWithItem.order.getIdItem()).equals(tvItemNumber.getText().toString()) &&
                formatter.format(orderWithItem.order.getPrice()).equals(etPrice.getText().toString()) &&
                orderWithItem.order.getStatus().equals(spStatus.getSelectedItem())) {
            return;

        }
        ;

        orderWithItem.order.setIdItem(tvItemNumber.getText().toString());
        orderWithItem.order.setPrice(Double.parseDouble(etPrice.getText().toString()));
        orderWithItem.order.setStatus(spStatus.getSelectedItem().toString());
        if (tvDeliver.getText().toString() != "")
            orderWithItem.order.setDeliveryDate(tvDeliver.getText().toString());

        /*viewModel.updateOrder(orderWithItem.order, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateOrder: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateOrder: failure", e);
            }
        });*/


    }

    private void displayDialogItemsList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an item");

        // add a list
        viewModel.getItems();
        OrderViewModel.Factory factory = new OrderViewModel.Factory(
                getApplication(), orderId);
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        viewModel.getItems().observe(this, itemsList -> {
            if (itemsList != null) {
                myItemsList = itemsList;
                String[] itemsId = new String[myItemsList.size()];
                int i = 0;
                for (ItemEntity item : itemsList) {
                    itemsId[i] = item.getIdItem() + " " + item.getName();
                    i++;
                }
                updateItemsList(builder, itemsId);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private void updateItemsList(AlertDialog.Builder builder, String[] itemsId) {
        builder.setItems(itemsId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tvItemNumber.setText(String.valueOf(myItemsList.get(position).getIdItem()));
                tvItemName.setText(myItemsList.get(position).getName());
                NumberFormat formatter = new DecimalFormat("#0.00");
                etPrice.setText(formatter.format(myItemsList.get(position).getPrice()));
                etPrice.setHint(formatter.format(myItemsList.get(position).getPrice()));
            }
        });
    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                return i;
            }
        }
        return index;
    }
}
