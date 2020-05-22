package com.example.skycastle;

import java.util.List;

public class UnivDetail_Item {
    public int type;
    public String text;
    public List<UnivDetail_Item> invisibleChildren;

    public UnivDetail_Item() {
    }

    public UnivDetail_Item(int type, String text) {
        this.type = type;
        this.text = text;
    }
}
