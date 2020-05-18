package com.example.skycastle;

public class HomeItem {
    String universityName;
    String applyType;
    String day;

    public HomeItem(String un, String an, String dn) {
        setUniversityName(un);
        setApplyType(an);
        setDay(dn);
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
}
