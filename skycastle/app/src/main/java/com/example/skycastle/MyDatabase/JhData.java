package com.example.skycastle.MyDatabase;

import java.io.Serializable;

public class JhData implements Serializable{
    String university;
    String sj;
    String jh;

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

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh;
    }

}
