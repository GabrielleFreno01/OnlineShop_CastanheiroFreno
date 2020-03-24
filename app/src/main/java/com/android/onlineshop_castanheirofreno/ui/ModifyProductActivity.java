package com.android.onlineshop_castanheirofreno.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.model.ItemModel;
import com.google.android.material.textfield.TextInputEditText;

public class ModifyProductActivity extends AppCompatActivity {

    private ImageButton productImage;
    private TextView idItem_tv;
    private TextInputEditText productName_input;
    private EditText productPrice_input;
    private EditText productQuantity_input;
    private Spinner spinner;
    private Button validateButton;
    private EditText productDescription_input;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_product);

        Intent intent = getIntent();

        int idItem = intent.getIntExtra("ID_ITEM", 0);

        //Replace by a function that gets the item with the idItem
        ItemModel itemModel = new ItemModel(idItem, R.drawable.apple_imac_1499, "Apple iMac", 1499.00);

        //Set default values of each element in the layout

        idItem_tv = findViewById(R.id.textview_id_order);
        idItem_tv.setText(Integer.toString(itemModel.getIdItem()));

        //Define the product image button
        productImage = findViewById(R.id.imagebtn_addImage);
        productImage.setImageResource(itemModel.getImage());
        productImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Check runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        //Permission not given
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //Show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }else{
                        //Permission already given
                        pickImageFromGallery();
                    }
                }

            }
        });

        productName_input = findViewById(R.id.input_productName);
        productName_input.setText(itemModel.getProductName());

        productPrice_input = findViewById(R.id.input_price);
        productPrice_input.setText(Double.toString(itemModel.getPrice()));

        productQuantity_input = findViewById(R.id.input_quantity);
        productQuantity_input.setText(Integer.toString(itemModel.getQuantity()));

        //Define the spinner
        spinner = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        validateButton = findViewById(R.id.btn_add_new_product);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductDescriptionActivity.class);
                startActivity(intent);

            }
        });

    }

    private void pickImageFromGallery(){
        Intent intent = new Intent (Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            productImage.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImageFromGallery();
        }
        else {
            Toast.makeText(this,"Gallery Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
