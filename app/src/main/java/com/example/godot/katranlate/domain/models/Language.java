package com.example.godot.katranlate.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 09/01/16.
 */
public class Language {
    private Integer id;
    private String iso;
    private String name;

    public static List<Language> fromCodes(String[] langCodes, String[] langNames) {
        List<Language> languages = new ArrayList<Language>();
        int i = 0;
        for (String code :
                langCodes) {
            languages.add(new Language(i, code, langNames[i]));
            i++;
        }

        return languages;
    }

    public Language(Integer id, String iso, String name) {
        this.id = id;
        this.iso = iso;
        this.name = name;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return iso;
    }
}