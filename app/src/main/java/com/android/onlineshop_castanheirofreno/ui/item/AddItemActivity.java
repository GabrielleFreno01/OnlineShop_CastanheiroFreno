package com.android.onlineshop_castanheirofreno.ui.item;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends BaseActivity {

    private Spinner spinner;
    //ImageButton imageButton;
    private Button validateButton;
    private EditText etproductName;
    private EditText etprice;
    private EditText etdescription;

    private static final String CANAL = "MyNotifCanal";

    private String idCategory;

    private MyListAdapter<CategoryEntity> adapterCategories;
    private List<CategoryEntity> categoryEntities;
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
        navigationView.setCheckedItem(R.id.nav_category);

        initiateView();

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
                viewModel.createItem(newItem, new OnAsyncEventListener(){

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
                /*new CreateItem(getApplication(), new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "createItem: success");
                        setResponse(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "createItem: failure", e);
                        setResponse(false);
                    }
                }).execute(newItem);*/
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
