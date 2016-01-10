package com.example.godot.katranlate.domain;
import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

import java.util.List;

/**
 * Created by sergi on 09/01/16.
 *
 */
public class GoogleTranslator implements Translator {

    @Override
    public String translate(String phrase, com.example.godot.katranlate.domain.models.Language from, com.example.godot.katranlate.domain.models.Language to) throws Exception {
        return null;
    }

    @Override
    public String translate(String phrase, com.example.godot.katranlate.domain.models.Language to) throws Exception {
        GoogleAPI.setHttpReferrer("http://android-er.blogspot.com/");
        GoogleAPI.setKey("key");
        String OutputString = Translate.DEFAULT.execute("Play",
                Language.ENGLISH, Language.FRENCH);
        return null;
    }

    @Override
    public List<com.example.godot.katranlate.domain.models.Language> getAvailableLanguages() {
        return null;
    }
}
