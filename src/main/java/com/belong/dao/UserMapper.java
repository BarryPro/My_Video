package com.belong.dao;

import com.belong.model.User;

import java.util.Map;

public interface UserMapper {
    User login(Map map);
    Integer getVip(Map map);
    boolean register(Map map);
    User getPic(int id);
    void updateVideoNumber(Map map);
    boolean updateUserVip(Map map);
    boolean updateSettingSave(Map map);
}
