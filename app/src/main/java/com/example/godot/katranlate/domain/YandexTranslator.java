package com.example.godot.katranlate.domain;

import com.example.godot.katranlate.domain.models.Language;
import com.example.godot.katranlate.net.Translate;
import com.rmtheis.yandtran.ApiKeys;
import com.rmtheis.yandtran.detect.Detect;

import java.util.List;

/**
 * Created by sergi on 09/01/16.
 */
public class YandexTranslator implements Translator{
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    String apiKey;

    public YandexTranslator(){
        Translate.setKey(ApiKeys.YANDEX_API_KEY);
    }

    public YandexTranslator(String apiKey) {
        this.apiKey = apiKey;
    }


    @Override
    public String translate(String phrase, final Language from, final Language to) throws Exception {
        return Translate.execute(phrase, from, to);
    }

    @Override
    public String translate(String phrase, Language to) throws Exception {
        // Language from = Detect.execute(phrase);
        Language from = null;
        return Translate.execute(phrase, from, to);
    }

    @Override
    public List<com.example.godot.katranlate.domain.models.Language> getAvailableLanguages() {
        return null;
    }
}
