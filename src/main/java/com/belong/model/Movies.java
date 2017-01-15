package com.belong.model;

import java.math.BigDecimal;

public class Movies {
    private Integer vid;

    private String vname;

    private String vdate;

    private Integer id;

    private BigDecimal views;

    private String vsrc;

    private byte[] vpic;

    private User user;
    private String vinfo;//影片信息
    private String  type;//上传的电影类型

    public String getVsrc() {
        return vsrc;
    }

    public void setVsrc(String  vsrc) {
        this.vsrc = vsrc;
    }

    public byte[] getVpic() {
        return vpic;
    }

    public void setVpic(byte[] vpic) {
        this.vpic = vpic;
    }

    public String getVinfo() {
        return vinfo;
    }

    public void setVinfo(String vinfo) {
        this.vinfo = vinfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname == null ? null : vname.trim();
    }

    public String getVdate() {
        return vdate;
    }

    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getViews() {
        return views;
    }

    public void setViews(BigDecimal views) {
        this.views = views;
    }
}
