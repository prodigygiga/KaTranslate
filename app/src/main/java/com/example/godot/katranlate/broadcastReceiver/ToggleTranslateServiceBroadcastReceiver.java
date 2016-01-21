package com.example.godot.katranlate.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.service.TranslateService;
import com.example.godot.katranlate.tools.Tools;

/**
 * Created by g.jobadze on 13/01/16.
 */
public class ToggleTranslateServiceBroadcastReceiver extends BroadcastReceiver {

    private Language fromLanguage;
    private Language toLanguage;
//    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isTranslationServiceRunning = Tools.isTranslateServiceRunning(context, TranslateService.class);


        fromLanguage = new Language(intent.getIntExtra("fromId", -1),
                intent.getStringExtra("fromIso"),
                intent.getStringExtra("fromName"));

        toLanguage = new Language(intent.getIntExtra("toId", -1),
                intent.getStringExtra("toIso"),
                intent.getStringExtra("toName"));

//        Toast.makeText(context, toLanguage.getIso(), Toast.LENGTH_SHORT).show();
        if (isTranslationServiceRunning) {
            Tools.stopTranslationService(context);
        } else {
            Tools.startTranslationService(context, fromLanguage, toLanguage);
        }
    }
}
