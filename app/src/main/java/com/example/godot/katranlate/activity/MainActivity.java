package com.example.godot.katranlate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.TextView;

import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;


import com.example.godot.katranlate.R;
import com.example.godot.katranlate.service.TranslateService;
import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

public class MainActivity extends Activity {
    Spinner languageFrom;
    Spinner languageTo;
    ImageView startServiceButton;
    boolean isTranslateServiceStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView resultTextView = (TextView)findViewById(R.id.activivty_main_resultTextView);

        Translate.setKey("trnsl.1.1.20160109T131535Z.6271dca148a14c1f.cbe71db4361720cd9082cd7c10c0e91263fd1a14");

        new AsyncTask<Void, Void, String>() {
            protected void onPreExecute() {
                // Pre Code
            }
            protected String doInBackground(Void... unused) {
                // Background Code
                String translatedText = "";
                try {
                    translatedText = Translate.execute("Hello", Language.ENGLISH, Language.GEORGIAN);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return translatedText;
            }
            protected void onPostExecute(String result) {
                resultTextView.setText(result);
            }
        }.execute();



        startService(new Intent(MainActivity.this, TranslateService.class));

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
        startService(new Intent(MainActivity.this,TranslateService.class));
        startServiceButton.setImageDrawable(getResources().getDrawable(R.drawable.stop));
        isTranslateServiceStarted = true;

    }
    private void stopTranslationService()
    {
        stopService(new Intent(MainActivity.this,TranslateService.class));
        startServiceButton.setImageDrawable(getResources().getDrawable(R.drawable.play));
        isTranslateServiceStarted = false;
    }
}
