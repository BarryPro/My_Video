package com.belong.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Movies implements Serializable{
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
}
