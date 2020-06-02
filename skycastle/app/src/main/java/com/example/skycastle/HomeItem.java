package com.example.skycastle;

import java.util.ArrayList;
import java.util.List;

public class HomeItem {
    private int showType;
    private String universityName;
    private String applyType;
    private String day;
    private Boolean isSelected;
    private List<HomeItem> invisibleChildren;

    public HomeItem(int st, String un, String an, String dn) {
        setShowType(st);
        setUniversityName(un);
        setApplyType(an);
        setDay(dn);
        setSelected(false);
    }

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

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public List<HomeItem> getInvisibleChildren() {
        return invisibleChildren;
    }

    public void setInvisibleChildren(List<HomeItem> invisibleChildren) {
        this.invisibleChildren = invisibleChildren;
    }
}
