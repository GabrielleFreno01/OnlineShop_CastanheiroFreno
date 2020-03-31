package com.android.onlineshop_castanheirofreno.ui.mgmt;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.customer.CustomerViewModel;

public class SettingsActivity extends BaseActivity {

    private Switch switch_connected;

    private EditText etEmail;

    private EditText etPassword;
    private CustomerViewModel viewModel;

    private CustomerEntity client;

    public static final String PREFS_CONNECT = "StayConnected";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle("Settings");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);



}
}