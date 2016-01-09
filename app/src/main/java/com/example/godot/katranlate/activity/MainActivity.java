package com.example.godot.katranlate.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.godot.katranlate.R;
import com.example.godot.katranlate.service.TranslateService;
import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

public class MainActivity extends AppCompatActivity {

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


    }
}
