package com.android.onlineshop_castanheirofreno.ui.mgmt;


import android.os.Bundle;
import android.widget.EditText;

import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.viewmodel.customer.CustomerViewModel;

public class SettingsActivity extends BaseActivity {


    private EditText etEmail;

    private EditText etPassword;
    private CustomerViewModel viewModel;

    private CustomerEntity client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle("Settings");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);


    }
}