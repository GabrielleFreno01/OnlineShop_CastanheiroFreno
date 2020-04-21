package com.android.onlineshop_castanheirofreno.ui.item;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.MyListAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.util.GlideApp;
import com.android.onlineshop_castanheirofreno.util.MyAppGlideModule;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class EditItemActivity extends BaseActivity {


    private Spinner spinner;
    private ImageButton imageButton;
    private Bitmap bitmap;
    private EditText etproductName;
    private EditText etprice;
    private EditText etdescription;

    private ItemViewModel viewModel;

    private ItemEntity oldItem;
    private ItemEntity newItem = new ItemEntity();
    private List<CategoryEntity> categoriesList;

    private FirebaseStorage storage;

    private boolean categoryChanged = false;
    private boolean imageChanged = false;

    private MyListAdapter<CategoryEntity> adapterCategories;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Item");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_edit_item, frameLayout);

        initiateView();

        storage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        String catId = intent.getStringExtra("idCategory");


        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemId, catId);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                oldItem = itemEntity;
                updateContent();
            }
        });

        categoriesList = new ArrayList<>();
        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                categoriesList = categories;
                updateSpinner();
            }
        });

        Button validateButton = (Button) findViewById(R.id.btn_edit_product);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);

            }
        });
    }

    private void updateSpinner() {
        adapterCategories.updateData(new ArrayList<>(categoriesList));
        int position = 0;
        for (int i=0; i<categoriesList.size(); i++) {
            CategoryEntity cat = categoriesList.get(i);
            if(cat.getIdCategory().equals(oldItem.getIdCategory())) {
                position = i;
                break;
            }
        }
        spinner.setSelection(position);

    }


    private void updateContent() {
        if (oldItem != null) {
            etproductName.setText(oldItem.getName());
            NumberFormat formatter = new DecimalFormat("#0.00");
            etprice.setText(formatter.format(oldItem.getPrice()));
            etdescription.setText(oldItem.getDescription());

            if (!imageChanged) {
                StorageReference imageRef = storage.getReference()
                        .child("images")
                        .child(oldItem.getIdItem() + ".jpg");

                Glide.with(this)
                        .load(imageRef)
                        .error(R.drawable.ic_add_a_photo_blue_24dp)
                        .signature(new ObjectKey(imageRef.getDownloadUrl()))
                        .into(imageButton);
            }

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
        }

    }

    public void save(View view) {
        saveChanges(
                etproductName.getText().toString(),
                Double.parseDouble(etprice.getText().toString()),
                etdescription.getText().toString(),
                (CategoryEntity)spinner.getSelectedItem()
        );


    }


    private void saveChanges(String name, double price, String description, CategoryEntity category) {
        newItem.setIdItem(oldItem.getIdItem());
        newItem.setName(name);
        newItem.setPrice(price);
        newItem.setDescription(description);
        newItem.setIdCategory(category.getIdCategory());
        if (imageChanged){
            imageButton.setDrawingCacheEnabled(true);
            imageButton.buildDrawingCache();

            StorageReference imageRef = storage.getReference()
                    .child("images")
                    .child(oldItem.getIdItem() + ".jpg");

            bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("EditItemActivity", "Upload image into Firebase Storage error : "+exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("EditItemActivity", "Upload image into Firebase Storage successful");
                }
            });

        }

        viewModel.updateItem(newItem, oldItem, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                if (!newItem.getIdCategory().equals(oldItem.getIdCategory()))
                    categoryChanged = true;
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
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
            Uri selectedImage = data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            //Get the column index of MediaStore.Images.Media.DATA
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //Gets the String value in the column
            String imgDecodableString = cursor.getString(columnIndex);

            cursor.close();

            bitmap = BitmapFactory.decodeFile(imgDecodableString);

            Glide.with(this)
                    .load(bitmap)
                    .error(R.drawable.ic_add_a_photo_blue_24dp)
                    .into(imageButton);
            imageChanged=true;
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

    private void setResponse(Boolean response) {
        if (response) {
            Toast toast = Toast.makeText(this, "Item edited", Toast.LENGTH_LONG);
            toast.show();
            Glide.get(this).clearMemory();
            if (categoryChanged==true) {
                setResult(RESULT_OK);
            }else{
                setResult(0);
            }
            finish();

        }
    }

    private void initiateView() {
        imageButton = findViewById(R.id.imagebtn_addImage);
        etproductName = findViewById(R.id.input_product_name);
        etprice = findViewById(R.id.input_price);
        etdescription = findViewById(R.id.input_description);

        spinner = findViewById(R.id.spinner_category);
        adapterCategories = new MyListAdapter<>(this, R.layout.row_category, new ArrayList<>());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategories);

    }

    @Override
    public void onBackPressed() {
        Glide.get(this).clearMemory();
        super.onBackPressed();
    }
}