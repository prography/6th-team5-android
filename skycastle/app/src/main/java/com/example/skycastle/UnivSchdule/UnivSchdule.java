package com.example.skycastle.UnivSchdule;

import java.util.ArrayList;

public class UnivSchdule {
    int num;
    String univ;
    String sj;
    String jh;
    String block;
    ArrayList<schedules> schedules;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUniv() {
        return univ;
    }

    public void setUniv(String univ) {
        this.univ = univ;
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

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public ArrayList<com.example.skycastle.UnivSchdule.schedules> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<com.example.skycastle.UnivSchdule.schedules> schedules) {
        this.schedules = schedules;
    }
}
