package com.belong.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class User implements Serializable{
    private static final long serialVersionUID =  2278481236798042747L;
    private Integer id;
    private String username;
    private String password;
    private Integer pagenum;
    private byte[] pic;
    private Integer vip;
    private String email;
    private Integer period;
    private Integer vipGrade;
    private String alias;
    private String login_time;
}
