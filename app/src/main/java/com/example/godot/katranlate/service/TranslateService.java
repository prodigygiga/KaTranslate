package com.example.godot.katranlate.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.example.godot.katranlate.domain.Translator;
import com.example.godot.katranlate.domain.YandexTranslator;
import com.example.godot.katranlate.domain.models.Language;

public class TranslateService extends Service {
    ClipboardManager clipboardManager;
    ClipData clipData;
    String clipString;
    ClipboardManager.OnPrimaryClipChangedListener clipChangedListener;

    public TranslateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        clipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {


                try {
                    clipData = clipboardManager.getPrimaryClip();

                    ClipData.Item item = clipData.getItemAt(0);
                    clipString = item.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new AsyncTask<String, Void, String>() {
                    protected void onPreExecute() {
                        // Pre Code
                    }

                    protected String doInBackground(String... translateWord) {
                        // Background Code
                        String translatedText = "";
                        Translator translator = new YandexTranslator();
                        try {
                            Language en = new Language(1, "en", "English");
                            Language ge = new Language(2, "ka", "Georgian");

                            translatedText = translator.translate(translateWord[0], en, ge);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return translatedText;
                    }

                    protected void onPostExecute(String result) {
                        Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
                    }
                }.execute(clipString);
            }
        };

        Toast.makeText(getBaseContext(), "Service Started", Toast.LENGTH_LONG).show();

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(clipChangedListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clipboardManager.removePrimaryClipChangedListener(clipChangedListener);
        Toast.makeText(getBaseContext(), "Service Stopped", Toast.LENGTH_SHORT).show();
//        stopSelf();

    }
}
