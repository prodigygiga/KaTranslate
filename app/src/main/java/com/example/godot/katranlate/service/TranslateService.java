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

        Toast.makeText(getBaseContext(),"Service Started",Toast.LENGTH_LONG).show();


        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                clipData = clipboardManager.getPrimaryClip();

                ClipData.Item item = clipData.getItemAt(0);
                clipString = item.getText().toString();

                new AsyncTask<String, Void, String>() {
                    protected void onPreExecute() {
                        Translate.setKey("trnsl.1.1.20160109T131535Z.6271dca148a14c1f.cbe71db4361720cd9082cd7c10c0e91263fd1a14");
                        // Pre Code
                    }
                    protected String doInBackground(String... translateWord) {
                        // Background Code
                        String translatedText = "";
                        try {
                            translatedText = Translate.execute(translateWord[0], Language.ENGLISH, Language.GEORGIAN);

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

    }
}
