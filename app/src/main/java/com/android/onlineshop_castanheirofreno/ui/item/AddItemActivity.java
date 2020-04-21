package com.android.onlineshop_castanheirofreno.ui.item;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.MyListAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.LoginActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends BaseActivity {

    private Spinner spinner;
    private ImageButton imageButton;
    private Bitmap bitmap;
    private Button validateButton;
    private EditText etproductName;
    private EditText etprice;
    private EditText etdescription;
    private boolean imageChanged;

    private static final String CANAL = "MyNotifCanal";

    private String idCategory;

    private MyListAdapter<CategoryEntity> adapterCategories;
    private List<CategoryEntity> categoryEntities;
    private Toast toast;

    private FirebaseStorage storage;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final String TAG = "AddItemActivity";

    private ItemViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_item, frameLayout);

        setTitle("Add item");
        navigationView.setCheckedItem(R.id.nav_category);

        initiateView();

        storage = FirebaseStorage.getInstance();

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), "", "");
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        categoryEntities = new ArrayList<>();
        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                categoryEntities = categories;
                updateSpinner(categories);
            }
        });


        validateButton = findViewById(R.id.btn_add_new_product);
        validateButton.setOnClickListener(view -> saveChanges(
                etproductName.getText().toString(),
                etdescription.getText().toString(),
                Double.parseDouble(etprice.getText().toString()),
                (CategoryEntity)spinner.getSelectedItem()
        ));


    }

    private void initiateView() {
        etproductName = findViewById(R.id.input_product_name);
        etdescription = findViewById(R.id.input_description);
        etprice = findViewById(R.id.input_price);
        etprice.setText("0.0");
        imageButton = findViewById(R.id.imagebtn_addImage);
        Glide.with(this).load(R.drawable.ic_add_a_photo_blue_24dp).into(imageButton);

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
            imageChanged = true;
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

    private void saveChanges(String name, String description, double price, CategoryEntity category) {


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
                ItemEntity newItem = new ItemEntity(name, description, price, category.getIdCategory());
                idCategory=category.getIdCategory();
                String idItem = viewModel.createItem(newItem, new OnAsyncEventListener(){

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "createItem: success");
                        setResponse(true);

                        //Send notification
                        String myMessage = "New product available !";

                        Log.d("FirebaseMassage" , "Notification received");

                        //action
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                        //Vibration
                        long[] vibrationPattern = {500, 1000};


                        //create notif
                        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplicationContext(), CANAL);
                        notifBuilder.setContentTitle("News");
                        notifBuilder.setContentText(myMessage);

                        notifBuilder.setContentIntent(pendingIntent);
                        notifBuilder.setVibrate(vibrationPattern);

                        //icon
                        notifBuilder.setSmallIcon(R.drawable.ic_notifications);


                        //send notif
                        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
                            String channelId = getString(R.string.notification_id);
                            String channelTitle = getString(R.string.notification_title);
                            String channelDescription = getString(R.string.notification_desc);
                            NotificationChannel channel = new NotificationChannel(channelId, channelTitle, NotificationManager.IMPORTANCE_DEFAULT);
                            channel.setDescription(channelDescription);
                            notifManager.createNotificationChannel(channel);
                            notifBuilder.setChannelId(channelId);


                        }
                        notifManager.notify(1, notifBuilder.build());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "createItem: failure", e);
                        setResponse(false);
                    }
                });
                if (imageChanged){
                    imageButton.setDrawingCacheEnabled(true);
                    imageButton.buildDrawingCache();

                    StorageReference imageRef = storage.getReference()
                            .child("images")
                            .child(idItem + ".jpg");

                    bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = imageRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("AddItemActivity", "Upload image into Firebase Storage error : "+exception.getMessage());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("AddItemActivity", "Upload image into Firebase Storage successful");
                        }
                    });

                }
            }
        }


    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, "Item created", Toast.LENGTH_LONG);
            toast.show();
            onBackPressed();
            finish();
        }
    }

}
