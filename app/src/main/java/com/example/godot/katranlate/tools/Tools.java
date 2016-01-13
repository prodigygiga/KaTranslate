package com.example.godot.katranlate.tools;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.godot.katranlate.R;
import com.example.godot.katranlate.broadcastReceiver.ToggleTranslateServiceBroadcastReceiver;
import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.service.TranslateService;

import java.util.List;

/**
 * Created by godot on 1/13/16.
 */
public class Tools {


    public static boolean isTranslateServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void initNotification(Context context, boolean isTranslationServiceRunning) {
        int imageId;
        if (isTranslationServiceRunning)
            imageId = R.drawable.stop;
        else
            imageId = R.drawable.play;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(context, ToggleTranslateServiceBroadcastReceiver.class);


        PendingIntent resultPendingIntent =
                PendingIntent.getBroadcast(
                        context, 0, resultIntent, 0
                );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(imageId)
                .setContentTitle("Title")
                .setAutoCancel(false)
                .setOngoing(false)
                .setContentText("Text");
        builder.setContentIntent(resultPendingIntent);

        Notification notification = builder.build();
        notificationManager.notify(0, notification);
    }


    public static boolean startTranslationService(Context context, Language selectedFromLanguage, Language selectedToLanguage) {
        Bundle extras = new Bundle();
        extras.putString("fromId", selectedFromLanguage.getId().toString());
        extras.putString("fromIso", selectedFromLanguage.getIso());
        extras.putString("fromName", selectedFromLanguage.getName());

        extras.putString("toId", selectedToLanguage.getId().toString());
        extras.putString("toIso", selectedToLanguage.getIso());
        extras.putString("toName", selectedFromLanguage.getName());

        Intent intent = new Intent(context, TranslateService.class);
        intent.putExtras(extras);

        context.startService(intent);
        initNotification(context, true);

        return true;


    }

    public static boolean stopTranslationService(Context context) {
        context.stopService(new Intent(context, TranslateService.class));
        initNotification(context, false);

        return false;
    }

}
