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

import com.example.godot.katranlate.activity.TranslatePopupActivity;
import com.example.godot.katranlate.domain.Translator;
import com.example.godot.katranlate.domain.YandexTranslator;
import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.net.Translate;
import com.example.godot.katranlate.tools.Tools;

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

//                    Toast.makeText(TranslateService.this, checkoutClip(clipString), Toast.LENGTH_SHORT).show();
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
                            String translationResult = result[0] + " - " + result[1];
//                            Toast.makeText(getBaseContext(), translationResult, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TranslateService.this, TranslatePopupActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            Bundle extras = new Bundle();
                            extras.putString("translationResult",translationResult);
                            intent.putExtras(extras);
                            startActivity(intent);
                            Tools.initNotification(TranslateService.this, true, fromLanguage, toLanguage, translationResult);
                        }

                    }.execute(checkoutClip(clipString));

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        };

        Toast.makeText(getBaseContext(), "თარგმნის სერვისი ჩაირთო", Toast.LENGTH_LONG).show();

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
        Toast.makeText(getBaseContext(), "თარგმნის სერვისი გამოირთო", Toast.LENGTH_SHORT).show();
        Tools.initNotification(TranslateService.this, false, fromLanguage, toLanguage);
//        stopSelf();

    }
}
