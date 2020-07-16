package com.example.skycastle.ServerData_full;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerData_full implements Serializable {
    String name;
    String logo;
    String review_url;
    ArrayList<sjs_full> sjs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getReview_url() {
        return review_url;
    }

    public void setReview_url(String review_url) {
        this.review_url = review_url;
    }

    public ArrayList<sjs_full> getSjs() {
        return sjs;
    }

    public void setSjs(ArrayList<sjs_full> sjs) {
        this.sjs = sjs;
    }
}