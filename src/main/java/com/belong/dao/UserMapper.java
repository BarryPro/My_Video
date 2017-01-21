package com.belong.dao;

import com.belong.model.User;

import java.util.Map;

public interface UserMapper {
    User login(Map map);
    boolean register(Map map);
    User getPic(int id);
    void updateVideoNumber(Map map);
}
