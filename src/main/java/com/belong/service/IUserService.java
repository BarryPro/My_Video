package com.belong.service;

import com.belong.model.User;

import java.util.Map;

/**
 * Created by belong on 16-11-5.
 */
public interface IUserService {
    boolean register(Map map);
    User login(Map map);
    Integer getVip(Map map);
    User getPic(int id);
    String getAuthor(int userid);
    boolean updataSelect(int value, int userid);
    void updateVideoNumber(Map map);
    boolean updateUserVip(Map map);
    boolean updateSettingSave(Map map);
    User getUserByUserId(Map map);
    boolean updateLoginTime(Map map);
    boolean updatePeriodByUserid(Map map);
}
