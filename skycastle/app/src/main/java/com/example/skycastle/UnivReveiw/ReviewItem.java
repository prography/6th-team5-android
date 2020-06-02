package com.example.skycastle.UnivReveiw;

import android.graphics.drawable.Drawable;

public class ReviewItem {
    private String univName;
    private Drawable iconDrawble;
    private String url;

    public ReviewItem(String univName, Drawable icon, String url) {
        this.univName = univName;
        this.iconDrawble = icon;
        this.url = url;
    }

    public String getUnivName() {
        return univName;
    }

    public void setUnivName(String univName) {
        this.univName = univName;
    }

    public Drawable getIconDrawble() {
        return iconDrawble;
    }

    public void setIconDrawble(Drawable iconDrawble) {
        this.iconDrawble = iconDrawble;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
