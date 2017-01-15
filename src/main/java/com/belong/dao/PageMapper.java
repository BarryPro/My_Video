package com.belong.dao;

import com.belong.model.Article;
import com.belong.model.Movies;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by belong on 2017/1/4.
 */
public interface PageMapper {
    ArrayList<Movies> getInfo(Map map);
    ArrayList<Movies> search(Map map);
    void upload(Map map);
    ArrayList<Article> query(Map map);
}
