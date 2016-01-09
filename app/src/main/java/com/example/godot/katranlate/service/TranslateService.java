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
import com.rmtheis.yandtran.language.Language;
import com.example.godot.katranlate.net.Translate;

public class TranslateService extends Service {
    ClipboardManager clipboardManager;
    ClipData clipData;
    String clipString;

    public TranslateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getBaseContext(),"Service Started",Toast.LENGTH_LONG).show();

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
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
//                            translatedText = translator.translate(translateWord[0], Language.ENGLISH, Language.GEORGIAN);

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
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getBaseContext(),"Service Stopped",Toast.LENGTH_SHORT).show();
//        stopSelf();

    }
}
