package com.belong.model;

public class ReviewWithBLOBs extends Review {
    private String vdirector;

    private String vactor;

    public String getVdirector() {
        return vdirector;
    }

    public void setVdirector(String vdirector) {
        this.vdirector = vdirector == null ? null : vdirector.trim();
    }

    public String getVactor() {
        return vactor;
    }

    public void setVactor(String vactor) {
        this.vactor = vactor == null ? null : vactor.trim();
    }
}