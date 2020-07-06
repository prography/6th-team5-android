package com.example.skycastle.ServerData;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerData implements Serializable {
    String name;
    String logo;
    String review_url;
    String like;
    ArrayList<sjs> sjs;

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

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public ArrayList<sjs> getSjs() {
        return sjs;
    }

    public void setSjs(ArrayList<sjs> sjs) {
        this.sjs = sjs;
    }
}
