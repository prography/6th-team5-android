package com.example.skycastle;

public class DataEvent {
    public final String eventText;
    public String sj="";
    public String jh="";
    public String major="";

    public DataEvent(String eventText) {
        this.eventText = eventText;
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
