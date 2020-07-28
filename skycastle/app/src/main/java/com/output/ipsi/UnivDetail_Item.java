package com.output.ipsi;

import java.util.List;

public class UnivDetail_Item {
    public int type;
    public String text;
    public String info_type;
    public List<UnivDetail_Item> invisibleChildren;

    public UnivDetail_Item(int type, String text, String info_type) {
        this.type = type;
        this.text = text;
        this.info_type=info_type;
    }

    public String getInfo_type() {
        return info_type;
    }

    public void setInfo_type(String info_type) {
        this.info_type = info_type;
    }
}
