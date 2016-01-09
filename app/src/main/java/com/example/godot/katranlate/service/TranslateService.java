package com.example.godot.katranlate.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

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

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                clipData = clipboardManager.getPrimaryClip();

                ClipData.Item item = clipData.getItemAt(0);
                clipString = item.getText().toString();

                new AsyncTask<Void, Void, String>() {
                    protected void onPreExecute() {
                        // Pre Code
                    }
                    protected String doInBackground(Void... unused) {
                        // Background Code
                        String translatedText = "";
                        try {
                            translatedText = Translate.execute("Hola, mundo!", Language.SPANISH, Language.ENGLISH);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return translatedText;
                    }
                    protected void onPostExecute(String result) {
                        Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
                    }
                }.execute();



            }
        });


    }
}
