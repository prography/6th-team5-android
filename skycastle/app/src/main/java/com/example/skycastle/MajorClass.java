package com.example.skycastle;

import java.util.HashMap;

public class MajorClass {
    String m_name;
    HashMap<String, String> schedule;

    public MajorClass(){

    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public HashMap<String, String> getSchedule() {
        return schedule;
    }

    public void setSchedule(String date, String detail) {
        schedule = new HashMap<String, String>();
        schedule.put(date,detail);
        this.schedule = schedule;
    }

}
