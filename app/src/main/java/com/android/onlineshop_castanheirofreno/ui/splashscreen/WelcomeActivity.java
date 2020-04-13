package com.android.onlineshop_castanheirofreno.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.viewmodel.customer.CustomerViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {


    private TextView welcome;

    private CustomerViewModel viewModel;

    private CustomerEntity client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        welcome = findViewById(R.id.welcome);


        CustomerViewModel.Factory factory = new CustomerViewModel.Factory(
                getApplication(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        viewModel = new ViewModelProvider(this, factory).get(CustomerViewModel.class);
        viewModel.getCustomer().observe(this, customerEntity -> {
            if (customerEntity != null) {
                client = customerEntity;
                welcome.setText("Welcome " + client.getFirstname() + " !");
            }
        });
        /*SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_USER, 0);
        String user = settings.getString(PREFS_USER, null);

        CustomerViewModel.Factory factory = new CustomerViewModel.Factory(getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(CustomerViewModel.class);
        viewModel.getCustomer().observe(this, customerEntity -> {
            if (customerEntity != null) {
                client = customerEntity;
                welcome.setText("Welcome " + client.getFirstName() + " !");
            }
        });*/

        //redirection vers la page principale après 1 secondes
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };

        new Handler().postDelayed(run, 2000);
    }

}

