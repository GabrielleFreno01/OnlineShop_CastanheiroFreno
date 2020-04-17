package com.android.onlineshop_castanheirofreno.ui.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.repository.CustomerRepository;
import com.android.onlineshop_castanheirofreno.ui.MainActivity;
import com.android.onlineshop_castanheirofreno.ui.splashscreen.WelcomeActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private CustomerRepository repository;

    private Toast toast;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPwd1;
    private EditText etPwd2;
    private EditText etCity;
    private EditText etCityCode;
    private EditText etTelephone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        repository = ((BaseApp) getApplication()).getCustomerRepository();

        initializeForm();
        toast = Toast.makeText(this, getString(R.string.client_created), Toast.LENGTH_LONG);
    }

    private void initializeForm() {
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etPwd1 = findViewById(R.id.password);
        etPwd2 = findViewById(R.id.passwordRep);
        etCity = findViewById(R.id.city);
        etCityCode = findViewById(R.id.city_code);
        etTelephone = findViewById(R.id.telephone);

        etCityCode.setText("0");


        Button saveBtn = findViewById(R.id.editButton);
        saveBtn.setOnClickListener(view -> saveChanges(
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etEmail.getText().toString(),
                etPwd1.getText().toString(),
                etPwd2.getText().toString(),
                etCity.getText().toString(),
                Integer.parseInt(etCityCode.getText().toString()),
                etTelephone.getText().toString()
        ));
    }

    private void saveChanges(String firstName, String lastName, String email, String pwd, String pwd2, String city, int city_code, String telephone) {
        boolean cancel = false;

        // Reset errors.
        etFirstName.setError(null);
        etLastName.setError(null);
        etEmail.setError(null);
        etCity.setError(null);
        etCityCode.setError(null);
        etTelephone.setError(null);

        if (email.equals("")) {
            etEmail.setError("This field is required !");
            cancel = true;
            etEmail.requestFocus();
            return;
        }

        if (firstName.equals("")) {
            etFirstName.setError("This field is required !");
            cancel = true;
            etFirstName.requestFocus();
            System.out.println(city_code);
            return;
        }
        if (lastName.equals("")) {
            etLastName.setError("This field is required !");
            cancel = true;
            etLastName.requestFocus();
            return;
        }

        if (city.equals("")) {
            etCity.setError("This field is required !");
            cancel = true;
            etCity.requestFocus();
            return;
        }

        if (city_code == 0) {
            etCityCode.setError("This field is required !");
            cancel = true;
            etCityCode.requestFocus();
            return;
        }

        if (telephone.equals("")) {
            etTelephone.setError("This field is required !");
            cancel = true;
            etTelephone.requestFocus();
            return;
        }

        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            cancel = true;
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            cancel = true;
            return;
        }

        if (!cancel) {

            CustomerEntity newClient = new CustomerEntity(email, firstName, lastName, city, city_code, telephone);

            repository.register(newClient, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createUserWithEmail: success");
                    setResponse(true);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "createUserWithEmail: failure", e);
                    setResponse(false);
                }


            }, etPwd1.getText().toString());
        }
    }

    private void setResponse(Boolean response) {
        if (response) {
            toast.show();
            Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            etEmail.setError(getString(R.string.error_used_email));
            etEmail.requestFocus();
        }
    }

}
