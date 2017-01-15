package com.belong.service;

import com.belong.dao.UserMapper;
import com.belong.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by belong on 2017/1/2.
 */
@Service
public class UserServiceImpl implements IUserService {
    private HashMap<String,String> types = new HashMap<String, String>();
    private static final String UPLOAD = "upload";
    private static final String RUSERNAME = "rusername";
    private static final String RPASSWORD = "rpassword";
    private static final String ENCODER = "utf-8";
    public UserServiceImpl(){
        types.put("image/jpeg", ".jpg");
        types.put("image/png", ".png");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
    }

    @Autowired
    private UserMapper dao;

    @Override
    public boolean register(Map map) {
        return dao.register(map);
    }


    @Override
    public User login(User user) {
        //用于返回正确的用户信息
        return dao.login(user);
    }

    @Override
    public User getPic(int id) {
        return dao.getPic(id);
    }

    @Override
    public String getAuthor(int userid) {
        return null;
    }

    @Override
    public boolean updataSelect(int value, int userid) {
        return false;
    }

    @Override
    public void updateVideoNumber(Map map) {
        dao.updateVideoNumber(map);
    }
}
