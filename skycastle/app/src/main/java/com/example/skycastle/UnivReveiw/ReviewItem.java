package com.example.skycastle.UnivReveiw;

import android.graphics.drawable.Drawable;

public class ReviewItem {
    private String univName;
    private String icon;
    private String url;

    public ReviewItem(String univName, String icon, String url) {
        this.univName = univName;
        this.icon = icon;
        this.url = url;
    }

    public String getUnivName() {
        return univName;
    }

    public void setUnivName(String univName) {
        this.univName = univName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
