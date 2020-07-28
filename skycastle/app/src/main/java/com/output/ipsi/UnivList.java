package com.output.ipsi;

import java.util.ArrayList;

public class UnivList {
    int id;
    String name;
    String logo;
    ArrayList<String> susi_n;
    ArrayList<String> susi_mb;
    ArrayList<String> jeongsi_mb;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

    public ArrayList<String> getSusi_n() {
        return susi_n;
    }

    public void setSusi_n(ArrayList<String> susi_n) {
        this.susi_n = susi_n;
    }

    public ArrayList<String> getSusi_mb() {
        return susi_mb;
    }

    public void setSusi_mb(ArrayList<String> susi_mb) {
        this.susi_mb = susi_mb;
    }

    public ArrayList<String> getJeongsi_mb() {
        return jeongsi_mb;
    }

    public void setJeongsi_mb(ArrayList<String> jeongsi_mb) {
        this.jeongsi_mb = jeongsi_mb;
    }

    /*public List<MajorClass> getMajorClass() {
        return majorclass_list;
    }

    public int setMajorClass(String major, String date, String detail) {
        for(int i =0; i<majorclass_list.size();i++){
            if(majorclass_list.get(i).m_name==major){
                majorclass_list.get(i).setSchedule(date,detail);
                return 0;
            }
        }
        MajorClass majorClass = new MajorClass();
        majorClass.setM_name(major);
        majorClass.setSchedule(date, detail);
        majorclass_list.add(majorClass);
        return 0;
    }*/
}
