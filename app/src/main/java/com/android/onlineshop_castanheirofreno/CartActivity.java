package com.android.onlineshop_castanheirofreno;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
    }

    public void seeConfirmation (View view){
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }

    public void goShopping (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public static class AddNewItemActivity extends AppCompatActivity {

        Spinner spinner;
        ImageButton imageButton;
        Button validateButton;

        private static final int IMAGE_PICK_CODE = 1000;
        private static final int PERMISSION_CODE = 1001;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_new_item);

            spinner = findViewById(R.id.spinner_category);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            imageButton = findViewById(R.id.imagebtn_addImage);
            imageButton.setOnClickListener(new View.OnClickListener() {

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
                imageButton.setImageURI(data.getData());
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
}
