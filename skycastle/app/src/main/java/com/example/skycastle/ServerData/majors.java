package com.example.skycastle.ServerData;

import java.io.Serializable;
import java.util.ArrayList;

public class majors implements Serializable {
    String name;
    ArrayList<schdules> schedules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<schdules> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<schdules> schedules) {
        this.schedules = schedules;
    }
}
