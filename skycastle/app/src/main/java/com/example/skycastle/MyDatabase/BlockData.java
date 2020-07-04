package com.example.skycastle.MyDatabase;

import java.io.Serializable;

public class BlockData implements Serializable {
    String university;
    String sj;
    String block;

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
