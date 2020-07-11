package com.example.skycastle;

import java.io.Serializable;

public class Univ_ServerSend implements Serializable {
    String univ_n;
    String sj;
    String jh;
    String major;

    public String getUniv_n() {
        return univ_n;
    }

    public void setUniv_n(String univ_n) {
        this.univ_n = univ_n;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
