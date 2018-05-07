package com.belong.dao;

import com.belong.model.User;

import java.util.Map;

public interface UserMapper {
    User login(Map map);
    Integer getVip(Map map);
    boolean register(Map map);
    User getPic(Map map);
    void updateVideoNumber(Map map);
    boolean updateUserVip(Map map);
    boolean updateSettingSave(Map map);
    User getUserByUserId(Map map);
    boolean updateLoginTime(Map map);
    boolean updatePeriodByUserid(Map map);
    boolean updateExtraByUserid(Map map);
    User getExtra(Map map);
    boolean updateVipGradeByUserid(Map map);
}
