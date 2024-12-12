package com.example.contactbook;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token)
    {
        Log.d("_____________", "Refreshed token: " + token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getNotification() != null) {
            showNotification();
        }
    }
    // Method to display the notifications
    public void showNotification()
    {
        Intent intent = new Intent(this, MainActivity.class);
        String channel_id = "notification_channel";

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id).setAutoCancel(true).setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 }).setOnlyAlertOnce(true).setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }
}
