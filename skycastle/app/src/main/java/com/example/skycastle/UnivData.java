package com.example.skycastle;

import java.util.ArrayList;
import java.util.HashMap;

public class UnivData {

    String name;
    String logo;
    ArrayList<susis> susis;
    ArrayList<susi_major_blocks> susi_major_blocks;
    ArrayList<jeongsis> jeongsis;
    ArrayList<jeongsi_major_blocks> jeongsi_major_blocks;

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

    public ArrayList<susi_major_blocks> getSusi_major_blocks() {
        return susi_major_blocks;
    }

    public void setSusi_major_blocks(ArrayList<susi_major_blocks> susi_major_blocks) {
        this.susi_major_blocks = susi_major_blocks;
    }

    public ArrayList<jeongsis> getJeongsis() {
        return jeongsis;
    }

    public void setJeongsis(ArrayList<jeongsis> jeongsis) {
        this.jeongsis = jeongsis;
    }

    public ArrayList<jeongsi_major_blocks> getJeongsi_major_blocks() {
        return jeongsi_major_blocks;
    }

    public void setJeongsi_major_blocks(ArrayList<jeongsi_major_blocks> jeongsi_major_blocks) {
        this.jeongsi_major_blocks = jeongsi_major_blocks;
    }
}
