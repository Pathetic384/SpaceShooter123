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

import java.util.Timer;
import java.util.TimerTask;

public class GameService extends Service {

    private static final String TAG = "GameService";
    private static final int NOTIFICATION_ID = 1;
    private Timer timer;
    private long startTime; // Variable to store game start time
    private long elapsedTime; // Variable to track elapsed time

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Game service created");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(NOTIFICATION_ID, createNotification());
        } else {
            // Create notification for Android 13 and above
            createNotificationChannel();
            Notification notification = createNotification();
            startForeground(NOTIFICATION_ID, notification);
        }
        startTime = System.currentTimeMillis();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Game service started");
        restartTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Game service destroyed");
        stopTimer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification() {
        createNotificationChannel();
        return new NotificationCompat.Builder(this, "game_service_channel")
                .setSmallIcon(R.drawable.bullet1)
                .setContentTitle("Time played")
                .setContentText("Time: 00:00:00")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "game_service_channel";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Game Service Notifications", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Notifications for when the game is running.");
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                    .createNotificationChannel(channel);
        }
    }

    private void restartTimer() {
        stopTimer();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateElapsedTime();
                updateNotification();
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void updateElapsedTime() {
        elapsedTime = System.currentTimeMillis() - startTime;
    }

//    private void updateNotification() {
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new NotificationCompat.Builder(this, "game_service_channel")
//                .setSmallIcon(R.drawable.bullet1)
//                .setContentTitle("Time played")
//                .setContentText("Time: " + formatTime(elapsedTime))
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .setOngoing(true)
//                .build();
//        notificationManager.notify(NOTIFICATION_ID, notification);
//    }

    private void updateNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "game_service_channel")
                .setSmallIcon(R.drawable.bullet1)
                .setContentTitle("Time played")
                .setContentText("Time: " + formatTime(elapsedTime))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }


    private String formatTime(long millis) {
        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((millis % (1000 * 60)) / 1000);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

