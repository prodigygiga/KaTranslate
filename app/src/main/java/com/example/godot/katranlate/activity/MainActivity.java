package com.example.godot.katranlate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;


import com.example.godot.katranlate.R;
import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.adapter.LanguageAdapter;
import com.example.godot.katranlate.service.TranslateService;

public class MainActivity extends AppCompatActivity {
    Spinner languageFrom;
    Spinner languageTo;
    ImageView startServiceButton;
    boolean isTranslateServiceStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("თარგმნა");

        isTranslateServiceStarted = false;


        languageFrom = (Spinner) findViewById(R.id.set_language_from);
        languageFrom.setAdapter(new LanguageAdapter(MainActivity.this,new Language[]{new Language(1,"ka","ქართული"),
                new Language(1,"ka","ქართული"),
                new Language(1,"ka","ქართული")}));


        languageTo = (Spinner) findViewById(R.id.set_language_to);
        languageTo.setAdapter(new LanguageAdapter(MainActivity.this,new Language[]{new Language(1,"ka","Georgian")}));

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
