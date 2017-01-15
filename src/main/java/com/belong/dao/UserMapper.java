package com.belong.dao;

import com.belong.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper {
    User login(@Param("user") User user);
    boolean register(Map map);
    User getPic(int id);
    void updateVideoNumber(Map map);
}
