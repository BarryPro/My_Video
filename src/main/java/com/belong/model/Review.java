package com.belong.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class Review implements Serializable{
    private Integer vid;
    private String vamount;
    private Movies video;
    private String vdirector;
    private String vactor;
}
