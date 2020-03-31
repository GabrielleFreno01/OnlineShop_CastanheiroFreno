package com.android.onlineshop_castanheirofreno.ui;

import android.content.Intent;
import android.os.Bundle;
import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.confirmation.ConfirmationActivity;
import com.android.onlineshop_castanheirofreno.ui.splashscreen.WelcomeActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Home");
        navigationView.setCheckedItem(position);
        getLayoutInflater().inflate(R.layout.activity_base, frameLayout);

        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NO_ANIMATION
        );
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_none);
    }


}
