package com.output.ipsi.ServerData;

import java.io.Serializable;
import java.util.ArrayList;

public class sjs implements Serializable {
    String sj;
    ArrayList<jhs> jhs;

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public ArrayList<jhs> getJhs() {
        return jhs;
    }

    public void setJhs(ArrayList<jhs> jhs) {
        this.jhs = jhs;
    }
}
