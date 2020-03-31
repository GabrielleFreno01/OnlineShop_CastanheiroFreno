package com.android.onlineshop_castanheirofreno.ui.item;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.MyListAdapter;
import com.android.onlineshop_castanheirofreno.database.async.customer.CreateCustomer;
import com.android.onlineshop_castanheirofreno.database.async.item.CreateItem;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.MainActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.RegisterActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends BaseActivity {

    private Spinner spinner;
    //ImageButton imageButton;
    private Button validateButton;
    private EditText etproductName;
    private EditText etprice;
    private EditText etdescription;

    private MyListAdapter<CategoryEntity> adapterCategories;
    private Toast toast;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final String TAG = "AddItemActivity";

    private ItemViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_item, frameLayout);

        setTitle("Add item");
        navigationView.setCheckedItem(position);

        initiateView();

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), 0L, 0L);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                updateSpinner(categories);
            }
        });


        validateButton = findViewById(R.id.btn_add_new_product);
        validateButton.setOnClickListener(view -> saveChanges(
                etproductName.getText().toString(),
                etdescription.getText().toString(),
                Double.parseDouble(etprice.getText().toString()),
                spinner.getSelectedItemId()+1
        ));


    }

    private void initiateView() {
        etproductName = findViewById(R.id.input_product_name);
        etdescription = findViewById(R.id.input_description);
        etprice = findViewById(R.id.input_price);
        etprice.setText("0.0");
        //imageButton = findViewById(R.id.imagebtn_addImage);

        spinner = findViewById(R.id.spinner_category);
        adapterCategories = new MyListAdapter<>(this, R.layout.row_category, new ArrayList<>());
        spinner.setAdapter(adapterCategories);
    }

    private void updateSpinner(List<CategoryEntity> categories) {
        adapterCategories.updateData(new ArrayList<>(categories));

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //imageButton.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImageFromGallery();
        } else {
            Toast.makeText(this, "Gallery Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveChanges(String name, String description, double price, long idCategory) {


            boolean cancel = false;

            // Reset errors.
            etproductName.setError(null);
            etdescription.setError(null);
            etprice.setError(null);


            if (name.equals("")) {
                etproductName.setError("This field is required !");
                cancel = true;
                etproductName.requestFocus();
                return;
            }

        if(price == 0. ) {
            etprice.setError("This field is required !");
            cancel = true;
            etprice.requestFocus();
            return;
        }

            if (description.equals("")) {
                etdescription.setError("This field is required !");
                cancel = true;
                etdescription.requestFocus();
                return;
            }

            if (!cancel) {
                ItemEntity newItem = new ItemEntity(name, description, price, idCategory);

                new CreateItem(getApplication(), new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "createItme: success");
                        setResponse(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "createItem: failure", e);
                        setResponse(false);
                    }
                }).execute(newItem);
            }
        }


    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, "Item created", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(AddItemActivity.this, CategoryActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
