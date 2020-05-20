package com.example.skycastle;

public class HomeItem {
    private String universityName;
    private String applyType;
    private String day;
    private Boolean isSelected;

    public HomeItem(String un, String an, String dn) {
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
}
