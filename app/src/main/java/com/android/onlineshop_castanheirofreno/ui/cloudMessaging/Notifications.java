package com.android.onlineshop_castanheirofreno.ui.cloudMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.mgmt.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notifications extends FirebaseMessagingService {

    private static final String CANAL = "NotifCanal";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, CANAL);
        notifBuilder.setContentTitle(title);
        notifBuilder.setContentText(message);

        notifBuilder.setSmallIcon(R.drawable.ic_notifications);

        //Vibration
        long[] vibrationPattern = {500, 1000};
        notifBuilder.setContentIntent(pendingIntent);
        notifBuilder.setVibrate(vibrationPattern);


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


}