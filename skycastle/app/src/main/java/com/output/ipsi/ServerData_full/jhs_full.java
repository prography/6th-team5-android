package com.output.ipsi.ServerData_full;

import java.io.Serializable;
import java.util.ArrayList;

public class jhs_full implements Serializable {
    String name;
    ArrayList<majors_full> majors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<majors_full> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<majors_full> majors) {
        this.majors = majors;
    }
}
