package com.example.skycastle.Home;

import android.graphics.drawable.Drawable;

import java.util.List;

public class HomeItem {
    // type 별로 저장해두는 data
    private int showType;
    private Drawable icon;
    private List<HomeItem> invisibleChildren;
    private String univId;

    public HomeItem(int st, String ui, String un, String an, String dn, String ed,String major) {
        setShowType(st);
        setUnivId(ui);
        setUniversityName(un);
        setApplyType(an);
        setDay(dn);
        setSelected(false);
        setEnd_day(ed);
        setMajor(major);
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getUnivId() {
        return univId;
    }

    public void setUnivId(String univId) {
        this.univId = univId;
    }

    public List<HomeItem> getInvisibleChildren() {
        return invisibleChildren;
    }

    public void setInvisibleChildren(List<HomeItem> invisibleChildren) {
        this.invisibleChildren = invisibleChildren;
    }

    // 공통 데이터
    private String universityName;
    private String applyType;
    private String day;
    private Boolean isSelected;
    private String end_day;
    private String major;

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
