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
    private Context context;

//    public ToggleTranslateServiceBroadcastReceiver() {
//
//    }
//
//    public ToggleTranslateServiceBroadcastReceiver(Context context, Language fromLanguage, Language toLanguage) {
//        this.fromLanguage = fromLanguage;
//        this.toLanguage = toLanguage;
//        this.context = context;
//    }


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isTranslationServiceRunning = Tools.isTranslateServiceRunning(context, TranslateService.class);
        if (isTranslationServiceRunning) {
            Tools.stopTranslationService(context);
        }
        else
        {
            Tools.startTranslationService(context,fromLanguage,toLanguage);
        }
    }
}
