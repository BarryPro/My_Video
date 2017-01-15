package com.belong.model;

public class Review {
    private Integer vid;
    private String vamount;
    private Movies video;
    private String vdirector;
    private String vactor;

    public Movies getVideo() {
        return video;
    }

    public void setVideo(Movies video) {
        this.video = video;
    }

    public String getVdirector() {
        return vdirector;
    }

    public void setVdirector(String vdirector) {
        this.vdirector = vdirector;
    }

    public String getVactor() {
        return vactor;
    }

    public void setVactor(String vactor) {
        this.vactor = vactor;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getVamount() {
        return vamount;
    }

    public void setVamount(String vamount) {
        this.vamount = vamount == null ? null : vamount.trim();
    }
}
