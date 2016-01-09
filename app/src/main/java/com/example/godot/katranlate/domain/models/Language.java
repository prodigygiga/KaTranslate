package com.example.godot.katranlate.domain.models;

/**
 * Created by sergi on 09/01/16.
 */
public class Language {
    private int id;
    private String iso;
    private String name;

    public Language(int id, String iso, String name) {
        this.id = id;
        this.iso = iso;
        this.name = name;
    }

    public int getId() {
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