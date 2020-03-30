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
import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.async.customer.CreateCustomer;
import com.android.onlineshop_castanheirofreno.database.async.item.CreateItem;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.MainActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.RegisterActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

public class AddItemActivity extends BaseActivity {

    Spinner spinner;
    //ImageButton imageButton;
    Button validateButton;
    EditText etproductName;
    EditText etprice;
    EditText etdescription;

    private Toast toast;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final String TAG = "AddItemActivity";

    ItemViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_item, frameLayout);

        setTitle("Add item");
        navigationView.setCheckedItem(position);

        initiateView();

        spinner = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //imageButton = findViewById(R.id.imagebtn_addImage);
        //imageButton.setOnClickListener(new View.OnClickListener() {
/*
            @Override
            public void onClick(View v) {
                //Check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //Permission not given
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //Show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //Permission already given
                        pickImageFromGallery();
                    }
                }

            }
        });
*/
        validateButton = findViewById(R.id.btn_add_new_product);
        validateButton.setOnClickListener(view -> saveChanges(
                etproductName.getText().toString(),
                etdescription.getText().toString(),
                Integer.parseInt(etprice.getText().toString()),
                spinner.getSelectedItemId()
                //imageButton.getId()
        ));


    }

    private void initiateView() {
        etproductName = findViewById(R.id.input_product_name);
        etdescription = findViewById(R.id.input_description);
        etprice = findViewById(R.id.input_price);
        spinner = findViewById(R.id.spinner_category);
        //imageButton = findViewById(R.id.imagebtn_addImage);

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

    private void saveChanges(String name, String description, int price, long idCategory){//, long idImage) {

        ItemEntity newItem = new ItemEntity(name, description, price, idCategory);//), idImage);

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

    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, "Item created", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}