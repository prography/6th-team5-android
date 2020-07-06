package com.example.skycastle.ServerData;

import java.io.Serializable;
import java.util.ArrayList;

public class jhs implements Serializable {
    String name;
    String like;
    ArrayList<majors> majors;

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

    public ArrayList<majors> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<majors> majors) {
        this.majors = majors;
    }
}
