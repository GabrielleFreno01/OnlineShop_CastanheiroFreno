package com.android.onlineshop_castanheirofreno.ui.customer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.android.onlineshop_castanheirofreno.viewmodel.customer.CustomerViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class CustomerActivity extends BaseActivity {

    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private static final String TAG = "CustomerActivity";

    private Toast toast;


    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPwd1;
    private EditText etPwd2;
    private EditText etTelephone;
    private EditText etCity;
    private EditText etCity_code;

    private Button btn_save;

    private CustomerViewModel viewModel;

    private CustomerEntity client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("My account");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_customer, frameLayout);

        initiateView();
        System.out.println( FirebaseAuth.getInstance().getCurrentUser().getUid());

        /*SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_CUSTOMERID, 0);
        String custoId = settings.getString(PREFS_CUSTOMERID, null);*/

        /*Intent intent = getIntent();
        String custoId = intent.getStringExtra("custoId");
        System.out.println(custoId);*/

        CustomerViewModel.Factory factory = new CustomerViewModel.Factory(
                getApplication(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        viewModel = new ViewModelProvider(this, factory).get(CustomerViewModel.class);
        viewModel.getCustomer().observe(this, customerEntity -> {
            if (customerEntity != null) {
                client = customerEntity;
                updateContent();
            }
        });



        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
                finish();

            }
        });

    }


    public void save(View view) {
        saveChanges(
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etEmail.getText().toString(),
                etPwd1.getText().toString(),
                etPwd2.getText().toString(),
                etTelephone.getText().toString(),
                etCity.getText().toString(),
                Integer.parseInt(etCity_code.getText().toString())
        );

        LinearLayout linearLayout = findViewById(R.id.clientPasswordLayout);
        linearLayout.setVisibility(View.GONE);
        etFirstName.setEnabled(false);
        etLastName.setEnabled(false);
        etEmail.setEnabled(false);
        etTelephone.setEnabled(false);
        etCity.setEnabled(false);
        etCity_code.setEnabled(false);
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
        menu.add(0, EDIT_CLIENT, Menu.NONE, getString(R.string.action_edit))
                .setIcon(R.drawable.ic_edit)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, DELETE_CLIENT, Menu.NONE, getString(R.string.action_delete))
                .setIcon(R.drawable.ic_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_CLIENT) {
            item.setIcon(R.drawable.ic_edit);
            EditableMode();
        }
        if (item.getItemId() == DELETE_CLIENT) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteClient(client, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteUser: success");
                        logout();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteUser: failure", e);
                    }
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etPwd1 = findViewById(R.id.password);
        etPwd2 = findViewById(R.id.passwordRep);
        etTelephone = findViewById(R.id.customer_telephone);
        etCity = findViewById(R.id.customer_city);
        etCity_code = findViewById(R.id.customer_city_code);

        etFirstName.setEnabled(false);
        etLastName.setEnabled(false);
        etEmail.setEnabled(false);
        etTelephone.setEnabled(false);
        etCity.setEnabled(false);
        etCity_code.setEnabled(false);


    }


    private void EditableMode() {
        // if (!isEditable) {

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

        etPwd1.setFocusable(true);
        etPwd1.setEnabled(true);
        etPwd1.setFocusableInTouchMode(true);

        etPwd2.setFocusable(true);
        etPwd2.setEnabled(true);
        etPwd2.setFocusableInTouchMode(true);

        etTelephone.setFocusable(true);
        etTelephone.setEnabled(true);
        etTelephone.setFocusableInTouchMode(true);

        etCity.setFocusable(true);
        etCity.setEnabled(true);
        etCity.setFocusableInTouchMode(true);

        etCity_code.setFocusable(true);
        etCity_code.setEnabled(true);
        etCity_code.setFocusableInTouchMode(true);


    }

    private void updateContent() {
        if (client != null) {
            etFirstName.setText(client.getFirstname());
            System.out.println(client.getFirstname());
            System.out.println(client.getLastname());
            etLastName.setText(client.getLastname());
            etEmail.setText(client.getEmail());
            etCity.setText(client.getCity());
            etCity_code.setText(String.valueOf(client.getCity_code()));
            etTelephone.setText(client.getTelephone());
            etPwd1.setText("");
            etPwd2.setText("");

        }
    }

    private void saveChanges(String firstName, String lastName, String email, String pwd, String pwd2, String telephone, String city, int city_code) {
        if (!(pwd.equals("")) && !(pwd2.equals(""))) {
            if (!pwd.equals(pwd2) || pwd.length() < 5) {
                toast = Toast.makeText(this, getString(R.string.error_edit_invalid_password), Toast.LENGTH_LONG);
                toast.show();
                etPwd1.setText("");
                etPwd2.setText("");
                return;
            }

            client.setEmail(email);
            client.setFirstname(firstName);
            client.setLastname(lastName);
            client.setPassword(pwd);
            client.setTelephone(telephone);
            client.setCity(city);
            client.setCity_code(city_code);

            viewModel.updateClientPwd(client, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Password update: success");
                    setResponse(true);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "Password update: failure", e);
                    setResponse(false);
                }
            });
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        } else {
            client.setEmail(email);
            client.setFirstname(firstName);
            client.setLastname(lastName);
            client.setTelephone(telephone);
            client.setCity(city);
            client.setCity_code(city_code);
        }


        viewModel.updateClient(client, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "update: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "update: failure", e);
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
