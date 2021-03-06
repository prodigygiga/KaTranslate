package com.example.godot.katranlate.domain;

import com.example.godot.katranlate.domain.models.Language;

import java.util.List;

/**
 * Created by sergi on 09/01/16.
 */
public interface Translator {
    public abstract String translate(String phrase, final Language from, final Language to) throws Exception;

    public abstract String translate(String phrase, final Language to) throws Exception;

    public abstract List<Language> getAvailableLanguages();
}
