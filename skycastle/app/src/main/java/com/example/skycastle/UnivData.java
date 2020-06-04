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

    public ArrayList<susis> getSusis() {
        return susis;
    }

    public void setSusis(ArrayList<susis> susis) {
        this.susis = susis;
    }

    public ArrayList<jeongsis> getJeongsis() {
        return jeongsis;
    }

    public void setJeongsis(ArrayList<jeongsis> jeongsis) {
        this.jeongsis = jeongsis;
    }

    public ArrayList<majors> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<majors> majors) {
        this.majors = majors;
    }

}
