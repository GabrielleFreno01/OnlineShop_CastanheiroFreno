package com.android.onlineshop_castanheirofreno.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.mgmt.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //redirection vers la page principale après 2 secondes
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        new Handler().postDelayed(run, 1500);
    }
}
