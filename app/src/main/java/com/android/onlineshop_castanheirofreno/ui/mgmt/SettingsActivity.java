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

        switch_connected = findViewById(R.id.switch_stay_connected);

        if(switch_connected.isChecked()){
            etEmail = findViewById(R.id.email_login);
            etPassword = findViewById(R.id.password_login);

            SharedPreferences settingsConnect = getSharedPreferences(PREFS_CONNECT, 0);
            String connect = settingsConnect.getString(PREFS_CONNECT, null);



            CustomerViewModel.Factory factory = new CustomerViewModel.Factory(getApplication(), connect);
            viewModel = ViewModelProviders.of(this, factory).get(CustomerViewModel.class);
            viewModel.getCustomer().observe(this, customerEntity -> {
                if (customerEntity != null) {
                    client = customerEntity;
                    etEmail.setText(client.getEmail());
                    etPassword.setText(client.getPassword());
                }
            });

        }else{
            SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
            editor.remove(BaseActivity.PREFS_USER);
            editor.apply();

        }

}
}