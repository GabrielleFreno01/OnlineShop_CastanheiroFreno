package com.android.onlineshop_castanheirofreno.ui.cart;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.confirmation.ConfirmationActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.ui.orders.OrdersActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;


public class CartActivity extends BaseActivity {


    private TextView etproductName;
    private TextView etproductPrice;
    private ImageView ivproductImage;
    private Button buy;
    private Button goShopping;
    private TextView etEmptyCart;
    private ImageView ivEmptyCart;
    private CardView cardView;

    private Toast toast;

    private ItemViewModel viewModel;

    private SimpleDateFormat dateformatter;

    private String formattedDate;

    private static final String CANAL = "MyNotifCanal";

    private static final String TAG = "AddOrder";

    ItemEntity item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create the activity
        super.onCreate(savedInstanceState);
        setTitle("My Cart");
        navigationView.setCheckedItem(R.id.nav_cart);
        getLayoutInflater().inflate(R.layout.activity_cart, frameLayout);

        initiateView();

        //get the item
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_ITEM, 0);
        String itemId = settings.getString(BaseActivity.PREFS_ITEM,"");

        SharedPreferences settingsUser = getSharedPreferences(BaseActivity.PREFS_USER, 0);
        String user = settingsUser.getString(BaseActivity.PREFS_USER, "");

        SharedPreferences settingsCat = getSharedPreferences(BaseActivity.PREFS_CATEGORYID, 0);
        String categoryId = settingsCat.getString(BaseActivity.PREFS_CATEGORYID, "");

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemId, categoryId);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                item = itemEntity;
                buy.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                etEmptyCart.setVisibility(View.GONE);
                ivEmptyCart.setVisibility(View.GONE);
                updateContent();
            } else {
                buy.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                etEmptyCart.setVisibility(View.VISIBLE);
                ivEmptyCart.setVisibility(View.VISIBLE);
            }


        });


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateformatter = new SimpleDateFormat("dd.MM.yyyy");
                formattedDate = dateformatter.format(System.currentTimeMillis());

                saveChanges((item.getPrice()), formattedDate, null, item.getIdItem(), item.getIdCategory(), "In progress", user);
                seeConfirmation(v);
                SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_ITEM, 0).edit();
                editor.remove(BaseActivity.PREFS_ITEM);
                editor.apply();
                SharedPreferences.Editor editor2 = getSharedPreferences(BaseActivity.PREFS_CATEGORYID, 0).edit();
                editor2.remove(BaseActivity.PREFS_CATEGORYID);
                editor2.apply();
                recreate();

            }
        });


        goShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShopping(v);
            }
        });

    }



    private void saveChanges(double price, String creationDate, String deliveryDate, String idItem, String idCategory, String status, String owner) {

        OrderEntity newOrder = new OrderEntity(price, creationDate, deliveryDate, idItem, idCategory, status, owner);

        viewModel.createOrder(newOrder, new OnAsyncEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess() {
                Log.d(TAG, "createOrder: success");
                setResponse(true);

                String myMessage = "Confirmation, your order passed successfully !";

                //action
                Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                //Vibration
                long[] vibrationPattern = {500, 1000};


                //create notif
                NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplicationContext(), CANAL);
                notifBuilder.setContentTitle("Your order");
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

    }

    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, "Order created", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void updateContent() {
        if (item != null) {
            NumberFormat defaultFormat = new DecimalFormat("#0.00");
            etproductName.setText(item.getName());
            etproductPrice.setText("CHF " + defaultFormat.format(item.getPrice()));
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference imageRef = storage.getReference()
                    .child("images")
                    .child(item.getIdItem()+".jpg");

            Glide.with(this)
                    .load(imageRef)
                    .error(R.drawable.ic_devices)
                    .signature(new ObjectKey(imageRef.getDownloadUrl()))
                    .into(ivproductImage);

        }
    }

    private void initiateView() {
        etproductName = findViewById(R.id.new_item_name);
        etproductPrice = findViewById(R.id.new_item_price);
        etEmptyCart = findViewById(R.id.empty_cart_tv);
        ivEmptyCart = findViewById(R.id.empty_cart_image);
        goShopping = findViewById(R.id.btn_go);
        buy = findViewById(R.id.btn_buy);
        cardView = findViewById(R.id.cart_item_cardview);
        ivproductImage = findViewById(R.id.item_image);
    }

    public void seeConfirmation(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
        finish();
    }

    public void goShopping(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
