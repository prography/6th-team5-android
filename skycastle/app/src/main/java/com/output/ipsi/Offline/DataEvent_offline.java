package com.output.ipsi.Offline;

public class DataEvent_offline {
    public final String eventText;
    public int date;
    public String type;

    DataEvent_offline(String eventText){this.eventText=eventText;}

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
