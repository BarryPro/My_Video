package com.belong.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by belong on 2017/1/15.
 */
@Controller
public class UserController {
    @Value("${user.username}")
    private String username;

    @RequestMapping("/")
    public String index(Map map){
        map.put("username",username);
        return "index";
    }
}
