package com.belong.model;

import java.io.Serializable;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Article implements Serializable{
    private Integer aid;
    private Integer uid;
    private Integer agree;
    private Integer disagree;
    private String adate;
    private Integer vid;
    private User user;
    private String acontent;
}
