package com.example.sectest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class GameService extends Service {

    private static final String TAG = "GameService";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Game service created");

        // Create notification for foreground service
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Game service started");
        // Handle any game initialization or background tasks here

        return START_STICKY; // Consider using START_STICKY for persistent service
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Game service destroyed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification() {
        // Configure notification channel (Android 8+ required)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "game_service_channel";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Game Service Notifications", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Notifications for when the game is running.");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        // Create notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "game_service_channel")
                .setSmallIcon(R.drawable.bullet1) // Replace with your game icon
                .setContentTitle("Game đang chạy")
                .setContentText("Game thực sự đang chạy")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true); // Mark as ongoing foreground service notification

        return builder.build();
    }
}
