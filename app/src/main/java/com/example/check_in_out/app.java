package com.example.check_in_out;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class app extends Application {

    public static final String channel = "channel_1";
    @Override
    public void onCreate() {
        super.onCreate();

        buildNotification();

    }

    private void buildNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channels = new NotificationChannel(channel, "Notification", NotificationManager.IMPORTANCE_HIGH);
            channels.setDescription("This person has overstayed");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channels);
        }
    }


}
