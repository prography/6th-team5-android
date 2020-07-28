package com.output.ipsi.ServerData_full;

import java.io.Serializable;
import java.util.ArrayList;

public class sjs_full implements Serializable {
    String sj;
    ArrayList<jhs_full> jhs;

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public ArrayList<jhs_full> getJhs() {
        return jhs;
    }

    public void setJhs(ArrayList<jhs_full> jhs) {
        this.jhs = jhs;
    }
}
