package com.example.skycastle;

import java.util.ArrayList;
import java.util.HashMap;

public class UnivData {

    String name;
    String logo;
    ArrayList<susis> susis;
    ArrayList<jeongsis> jeongsis;
    ArrayList<majors> majors;

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

    public ArrayList<com.example.skycastle.susis> getSusis() {
        return susis;
    }

    public void setSusis(ArrayList<com.example.skycastle.susis> susis) {
        this.susis = susis;
    }

    public ArrayList<com.example.skycastle.jeongsis> getJeongsis() {
        return jeongsis;
    }

    public void setJeongsis(ArrayList<com.example.skycastle.jeongsis> jeongsis) {
        this.jeongsis = jeongsis;
    }

    public ArrayList<com.example.skycastle.majors> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<com.example.skycastle.majors> majors) {
        this.majors = majors;
    }

}
