package com.belong.service;

import com.belong.model.Article;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by belong on 16-11-16.
 */
public interface IArticleService {
    void addArticle(Map map);
    ArrayList<Article> queryArticle(Map map);
    void deleteArticle(Map map);
    void updateArticle(Map map);
    Article queryArticleByAid(Map map);
    void updateAgree(Map map);
    void updataDisagree(Map map);
}
