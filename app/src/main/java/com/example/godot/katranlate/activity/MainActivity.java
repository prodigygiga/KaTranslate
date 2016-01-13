package com.example.godot.katranlate.activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;


import com.example.godot.katranlate.R;
import com.example.godot.katranlate.broadcastReceiver.ToggleTranslateServiceBroadcastReceiver;
import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.adapter.LanguageAdapter;
import com.example.godot.katranlate.service.TranslateService;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Spinner languageFromSpinner;
    Spinner languageToSpinner;
    ImageView startServiceButton;
    boolean isTranslateServiceStarted;
    Language selectedFromLanguage;
    Language selectedToLanguage;
    NotificationCompat.Builder builder;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.translate));


//        builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.stop)
//                .setContentTitle("Title")
//                .setAutoCancel(false)
//                .setOngoing(true)
//                .setContentText("Text");
//
//        Intent resultIntent = new Intent(getBaseContext(), AutoLoad.class);
//
//
////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////
////        stackBuilder.addParentStack(TranslateService.class);
////
////        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                PendingIntent.getBroadcast(
//                        getBaseContext(), 0, resultIntent, 0
//                );
//        builder.setContentIntent(resultPendingIntent);
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        mNotificationManager.notify(0, builder.build());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(this, ToggleTranslateServiceBroadcastReceiver.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getBroadcast(
                        getBaseContext(), 0, resultIntent, 0
                );
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.stop)
                .setContentTitle("Title")
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentText("Text");
        builder.setContentIntent(resultPendingIntent);

        Notification notification = builder.build();
        notificationManager.notify(0, notification);


        isTranslateServiceStarted = false;

        List<Language> langs = Language.fromCodes(
                getResources().getStringArray(R.array.lang_codes),
                getResources().getStringArray(R.array.lang_names));

        selectedFromLanguage = langs.get(0);
        selectedToLanguage = langs.get(1);

        languageFromSpinner = (Spinner) findViewById(R.id.set_language_from);
        languageFromSpinner.setAdapter(new LanguageAdapter(MainActivity.this, langs));
        languageFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopTranslationService();
                selectedFromLanguage = (Language) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        languageToSpinner = (Spinner) findViewById(R.id.set_language_to);
        languageToSpinner.setAdapter(new LanguageAdapter(MainActivity.this, langs));
        languageToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopTranslationService();
                selectedToLanguage = (Language) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        languageToSpinner.setSelection(selectedToLanguage.getId());

        ImageView exchangeLangButton = (ImageView) findViewById(R.id.exchange_image);
        exchangeLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tmpID;
                tmpID = (int) languageFromSpinner.getSelectedItemId();
                languageFromSpinner.setSelection((int) languageToSpinner.getSelectedItemId(), true);

                languageToSpinner.setSelection(tmpID, true);
            }
        });

        startServiceButton = (ImageView) findViewById(R.id.start_service_button);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTranslateServiceStarted) {
                    startTranslationService();
                } else {
                    stopTranslationService();
                }

            }
        });

    }

    private void startTranslationService() {
        Bundle extras = new Bundle();
        extras.putString("fromId", selectedFromLanguage.getId().toString());
        extras.putString("fromIso", selectedFromLanguage.getIso());
        extras.putString("fromName", selectedFromLanguage.getName());

        extras.putString("toId", selectedToLanguage.getId().toString());
        extras.putString("toIso", selectedToLanguage.getIso());
        extras.putString("toName", selectedFromLanguage.getName());

        Intent intent = new Intent(MainActivity.this, TranslateService.class);
        intent.putExtras(extras);

        startService(intent);
        startServiceButton.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.stop));
        isTranslateServiceStarted = true;

    }

    private void stopTranslationService() {
        stopService(new Intent(MainActivity.this, TranslateService.class));
        startServiceButton.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.play));
        isTranslateServiceStarted = false;
    }


}