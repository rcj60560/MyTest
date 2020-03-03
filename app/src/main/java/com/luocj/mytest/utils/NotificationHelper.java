package com.luocj.mytest.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.luocj.mytest.MainActivity;
import com.luocj.mytest.R;
import com.luocj.mytest.activity.net.InstallActivity;

import java.util.zip.Inflater;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    private static final String CHANNEL_ID = "ID";
    private static final CharSequence CHANNEL_NAME = "NAME";
    private NotificationManager notificationManager;
    private NotificationCompat.Builder updateBuilder;


    public static NotificationHelper getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static NotificationHelper instance = new NotificationHelper();
    }


    public void init(Context context) {
        Intent intent = new Intent(context, InstallActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //8.0通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(false);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }

        updateBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setProgress(0, 0, true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    public void showUpDateNotification(String title, String content, int progress) {
        updateBuilder.setContentTitle(title);
        updateBuilder.setContentText(progress + "%");
        updateBuilder.setProgress(100, progress, false);
        Notification notification = updateBuilder.build();
        notificationManager.notify(1, notification);
    }

    public void dismissUpdateNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateBuilder.setContentIntent(pendingIntent);
    }
}
