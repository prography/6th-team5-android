package com.example.skycastle.ServerData;

import java.io.Serializable;
import java.util.ArrayList;

public class jhs implements Serializable {
    String name;
    ArrayList<majors> majors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<majors> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<majors> majors) {
        this.majors = majors;
    }
}
