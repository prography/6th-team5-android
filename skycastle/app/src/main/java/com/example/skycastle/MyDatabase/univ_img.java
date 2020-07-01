package com.example.skycastle.MyDatabase;

import java.io.Serializable;

public class univ_img implements Serializable {
    String university;
    String sj;

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }
}
