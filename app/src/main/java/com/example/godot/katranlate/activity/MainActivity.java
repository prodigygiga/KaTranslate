package com.example.godot.katranlate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.godot.katranlate.R;
import com.example.godot.katranlate.adapter.LanguageAdapter;
import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.service.TranslateService;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Spinner languageFrom;
    Spinner languageTo;
    ImageView startServiceButton;
    boolean isTranslateServiceStarted;
    Language selectedFromLanguage;
    Language selectedToLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        setTitle("თარგმნა");

        isTranslateServiceStarted = false;

        List<Language> langs = Language.fromCodes(
                getResources().getStringArray(R.array.lang_codes),
                getResources().getStringArray(R.array.lang_names));

        selectedFromLanguage = langs.get(0);
        selectedToLanguage = langs.get(1);

        languageFrom = (Spinner) findViewById(R.id.set_language_from);
        languageFrom.setAdapter(new LanguageAdapter(MainActivity.this, langs));
        languageFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopTranslationService();
                selectedFromLanguage = (Language) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        languageTo = (Spinner) findViewById(R.id.set_language_to);
        languageTo.setAdapter(new LanguageAdapter(MainActivity.this, langs));
        languageTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopTranslationService();
                selectedToLanguage = (Language) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        languageTo.setSelection(selectedToLanguage.getId());



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
        extras.putString("fromId",selectedFromLanguage.getId().toString());
        extras.putString("fromIso",selectedFromLanguage.getIso());
        extras.putString("fromName",selectedFromLanguage.getName());

        extras.putString("toId",selectedToLanguage.getId().toString());
        extras.putString("toIso",selectedToLanguage.getIso());
        extras.putString("toName",selectedFromLanguage.getName());

        Intent intent = new Intent(MainActivity.this, TranslateService.class);
        intent.putExtras(extras);

        startService(intent);
        startServiceButton.setImageDrawable(getResources().getDrawable(R.drawable.stop));
        isTranslateServiceStarted = true;

    }

    private void stopTranslationService() {
        stopService(new Intent(MainActivity.this, TranslateService.class));
        startServiceButton.setImageDrawable(getResources().getDrawable(R.drawable.play));
        isTranslateServiceStarted = false;
    }
}
