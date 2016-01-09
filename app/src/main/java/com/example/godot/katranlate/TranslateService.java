package com.example.godot.katranlate;

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
}
