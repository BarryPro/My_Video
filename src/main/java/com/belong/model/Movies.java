package com.belong.model;

import java.math.BigDecimal;
import java.util.Date;

public class Movies {
    private Integer vid;

    private Date vdate;

    private Integer id;

    private BigDecimal views;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Date getVdate() {
        return vdate;
    }

    public void setVdate(Date vdate) {
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