package com.android.onlineshop_castanheirofreno.ui.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.MainActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class CustomerActivity extends BaseActivity {

    private static final int EDIT_CUSTOMER = 1;
    private static final int DELETE_CUSTOMER = 2;

    private Toast toast;

    private boolean isEditable;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPwd1;
    private EditText etPwd2;
    private EditText etTelephone;
    private EditText etCity;
    private EditText etCity_code;


    private CustomerViewModel viewModel;

    private CustomerEntity customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_activity_client);
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_customer, frameLayout);

        initiateView();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(PREFS_USER, null);

        CustomerViewModel.Factory factory = new CustomerViewModel.Factory(getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(CustomerViewModel.class);
        viewModel.getCustomer().observe(this, customertEntity -> {
            if (customertEntity != null) {
                customer = customertEntity;
                updateContent();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        finish();
        return super.onNavigationItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, EDIT_CUSTOMER, Menu.NONE, getString(R.string.action_edit))
                .setIcon(R.drawable.ic_edit)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, EDIT_CUSTOMER, Menu.NONE, getString(R.string.action_delete))
                .setIcon(R.drawable.ic_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_CUSTOMER) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_edit);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_CUSTOMER) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteCustomer(customer, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        logout();
                    }

                    @Override
                    public void onFailure(Exception e) {}
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        isEditable = false;
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etPwd1 = findViewById(R.id.password);
        etPwd2 = findViewById(R.id.passwordRep);
        etTelephone = findViewById(R.id.customer_telephone);
        etCity = findViewById(R.id.customer_city);
        etCity_code = findViewById(R.id.customer_city_code);

    }

    private void switchEditableMode() {
        if (!isEditable) {
            LinearLayout linearLayout = findViewById(R.id.clientPasswordLayout);
            linearLayout.setVisibility(View.VISIBLE);
            etFirstName.setFocusable(true);
            etFirstName.setEnabled(true);
            etFirstName.setFocusableInTouchMode(true);

            etLastName.setFocusable(true);
            etLastName.setEnabled(true);
            etLastName.setFocusableInTouchMode(true);

            etEmail.setFocusable(true);
            etEmail.setEnabled(true);
            etEmail.setFocusableInTouchMode(true);

            etTelephone.setFocusable(true);
            etTelephone.setEnabled(true);
            etTelephone.setFocusableInTouchMode(true);

            etCity.setFocusable(true);
            etCity.setEnabled(true);
            etCity.setFocusableInTouchMode(true);

            etCity_code.setFocusable(true);
            etCity_code.setEnabled(true);
            etCity_code.setFocusableInTouchMode(true);
            etCity_code.requestFocus();

        } else {
            saveChanges(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etEmail.getText().toString(),
                    etPwd1.getText().toString(),
                    etPwd2.getText().toString(),
                    etTelephone.getText().toString(),
                    etCity.getText().toString()
                    //Integer.parseInt(etCity_code.toString())
            );
            LinearLayout linearLayout = findViewById(R.id.clientPasswordLayout);
            linearLayout.setVisibility(View.GONE);
            etFirstName.setFocusable(false);
            etFirstName.setEnabled(false);
            etLastName.setFocusable(false);
            etLastName.setEnabled(false);
            etEmail.setFocusable(false);
            etEmail.setEnabled(false);
            etTelephone.setFocusable(false);
            etTelephone.setEnabled(false);
            etCity.setFocusable(false);
            etCity.setEnabled(false);
            etCity_code.setFocusable(false);
            etCity_code.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    private void updateContent() {
        if (customer != null) {
            etFirstName.setText(customer.getFirstName());
            etLastName.setText(customer.getLastName());
            etEmail.setText(customer.getEmail());
            etTelephone.setText(customer.getTelephone());
            etCity.setText(customer.getCity());
            //etCity_code.setText(customer.getCity_code());
        }
    }

    private void saveChanges(String firstName, String lastName, String email, String pwd, String pwd2, String telephone, String city){//, int city_code) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            toast = Toast.makeText(this, getString(R.string.error_edit_invalid_password), Toast.LENGTH_LONG);
            toast.show();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPassword(pwd);
        customer.setCity(city);
        //customer.setCity_code(city_code);
        customer.setTelephone(telephone);

        viewModel.updateCustomer(customer, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
            toast = Toast.makeText(this, getString(R.string.client_edited), Toast.LENGTH_LONG);
            toast.show();
        } else {
            etEmail.setError(getString(R.string.error_used_email));
            etEmail.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}
