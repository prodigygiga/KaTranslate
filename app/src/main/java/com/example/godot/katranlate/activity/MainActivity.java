package com.example.godot.katranlate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;


import com.example.godot.katranlate.R;
import com.example.godot.katranlate.service.TranslateService;

public class MainActivity extends Activity {
    Spinner languageFrom;
    Spinner languageTo;
    ImageView startServiceButton;
    boolean isTranslateServiceStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTranslateServiceStarted = false;


        languageFrom = (Spinner) findViewById(R.id.set_language_from);
        languageTo = (Spinner) findViewById(R.id.set_language_to);

        startServiceButton = (ImageView) findViewById(R.id.start_service_button);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTranslateServiceStarted)
                {
                    startTranslationService();
                }
                else
                {
                    stopTranslationService();
                }

            }
        });

    }
    private void startTranslationService()
    {
        startService(new Intent(MainActivity.this, TranslateService.class));
        startServiceButton.setImageDrawable(getResources().getDrawable(R.drawable.stop));
        isTranslateServiceStarted = true;

    }
    private void stopTranslationService()
    {
        stopService(new Intent(MainActivity.this, TranslateService.class));
        startServiceButton.setImageDrawable(getResources().getDrawable(R.drawable.play));
        isTranslateServiceStarted = false;
    }
}
