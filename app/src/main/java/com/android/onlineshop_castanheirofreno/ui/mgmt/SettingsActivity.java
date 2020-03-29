package com.android.onlineshop_castanheirofreno.ui.mgmt;


import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;

import androidx.appcompat.app.AppCompatDelegate;

import com.android.onlineshop_castanheirofreno.R;

public class SettingsActivity extends BaseActivity {

    private Switch switch_dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle("Settings");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_customer, frameLayout);

}
}