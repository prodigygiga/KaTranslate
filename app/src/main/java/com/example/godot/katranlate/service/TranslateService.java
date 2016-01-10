package com.example.godot.katranlate.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.example.godot.katranlate.domain.Translator;
import com.example.godot.katranlate.domain.YandexTranslator;
import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.net.Translate;

public class TranslateService extends Service {

    ClipboardManager clipboardManager;
    ClipData clipData;
    String clipString;
    ClipboardManager.OnPrimaryClipChangedListener clipChangedListener;
    Language fromLanguage;
    Language toLanguage;

    public TranslateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Bundle extras = intent.getExtras();
        fromLanguage = new Language(Integer.parseInt(extras.get("fromId").toString()),
                extras.get("fromIso").toString(), extras.get("fromName").toString());
        toLanguage = new Language(Integer.parseInt(extras.get("toId").toString()),
                extras.get("toIso").toString(), extras.get("toName").toString());

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

                new AsyncTask<String, Void, String[]>() {
                    protected void onPreExecute() {
                        // Pre Code
                    }

                    protected String[] doInBackground(String... translateWord) {
                        // Background Code
                        String translatedText = "";
                        Translator translator = new YandexTranslator();
                        try {
//                            Language en = new Language(1, "en", "English");
//                            Language ge = new Language(2, "ka", "Georgian");

                            translatedText = translator.translate(translateWord[0], fromLanguage, toLanguage);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return new String[]{translateWord[0], translatedText};
                    }

                    protected void onPostExecute(String[] result) {
                        Toast.makeText(getBaseContext(), result[0] + " - " + result[1], Toast.LENGTH_SHORT).show();
                    }

                }.execute(checkoutClip(clipString));
            }
        };

        Toast.makeText(getBaseContext(), "Service Started", Toast.LENGTH_LONG).show();

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(clipChangedListener);
    }

    private String checkoutClip(String clip) {
        if (clip.contains(" ")) {
            String[] parts = clip.split(" ");
            return parts[0];
        } else {
            return clip;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clipboardManager.removePrimaryClipChangedListener(clipChangedListener);
        Toast.makeText(getBaseContext(), "Service Stopped", Toast.LENGTH_SHORT).show();
//        stopSelf();

    }
}
