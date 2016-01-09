package com.example.godot.katranlate.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

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

                Toast.makeText(getBaseContext(),clipString,Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        Toast.makeText(getBaseContext(),"Service Stopped",Toast.LENGTH_SHORT).show();

    }
}
