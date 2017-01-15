package com.belong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by belong on 2017/1/15.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "video/home";
    }
}
