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
import android.widget.Toast;


import com.example.godot.katranlate.R;
import com.example.godot.katranlate.broadcastReceiver.ToggleTranslateServiceBroadcastReceiver;
import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.adapter.LanguageAdapter;
import com.example.godot.katranlate.service.TranslateService;
import com.example.godot.katranlate.tools.Tools;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Spinner languageFromSpinner;
    Spinner languageToSpinner;
    ImageView startServiceButton;
    boolean isTranslateServiceStarted;
    Language selectedFromLanguage;
    Language selectedToLanguage;
    NotificationCompat.Builder builder;


    @Override
    protected void onResume() {
        super.onResume();


        isTranslateServiceStarted = Tools.isTranslateServiceRunning(MainActivity.this,TranslateService.class);

        initStartButton(isTranslateServiceStarted);
        Tools.initNotification(MainActivity.this, isTranslateServiceStarted);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.translate));

        isTranslateServiceStarted = Tools.isTranslateServiceRunning(MainActivity.this,TranslateService.class);
        Tools.initNotification(MainActivity.this,isTranslateServiceStarted);





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
                isTranslateServiceStarted = Tools.stopTranslationService(MainActivity.this);
                initStartButton(isTranslateServiceStarted);
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
                isTranslateServiceStarted = Tools.stopTranslationService(MainActivity.this);
                initStartButton(isTranslateServiceStarted);
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

        initStartButton(isTranslateServiceStarted);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTranslateServiceStarted) {
                    isTranslateServiceStarted = Tools.startTranslationService(MainActivity.this, selectedFromLanguage, selectedToLanguage);
                    initStartButton(isTranslateServiceStarted);
                } else {
                    isTranslateServiceStarted = Tools.stopTranslationService(MainActivity.this);
                    initStartButton(isTranslateServiceStarted);
                }

            }
        });

    }


    private void initStartButton(boolean isTranslateServiceStarted) {
        if (isTranslateServiceStarted)
            startServiceButton.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.stop));
        else
            startServiceButton.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.play));

    }



}