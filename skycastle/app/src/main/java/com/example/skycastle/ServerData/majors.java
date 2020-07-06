package com.example.skycastle.ServerData;

import java.io.Serializable;
import java.util.ArrayList;

public class majors implements Serializable {
    String name;
    String like;
    ArrayList<schdules> schedules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public ArrayList<schdules> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<schdules> schedules) {
        this.schedules = schedules;
    }
}
