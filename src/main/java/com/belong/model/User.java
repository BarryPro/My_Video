package com.belong.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class User implements Serializable{
    private Integer id;
    private String username;
    private String password;
    private Integer pagenum;
    private byte[] pic;
}
