package com.belong.model;

import java.io.Serializable;

public class Type implements Serializable{
    private Integer vid;

    private String tid;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }
}
