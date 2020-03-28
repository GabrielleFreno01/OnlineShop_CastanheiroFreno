package com.android.onlineshop_castanheirofreno.ui.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.customer.CustomerViewModel;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.LoginActivity;

public class WelcomeActivity  extends AppCompatActivity {



    private TextView welcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //redirection vers la page principale après 2 secondes
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };

        new Handler().postDelayed(run, 1000);
    }

}

