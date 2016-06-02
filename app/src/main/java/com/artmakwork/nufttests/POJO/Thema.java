package com.artmakwork.nufttests.POJO;

public class Thema {

    private int theme_id;
    private String theme;

    public Thema(int theme_id, String theme ) {
        this.theme = theme;
        this.theme_id = theme_id;
    }

    @Override
    public String toString() {
        return theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }
}
