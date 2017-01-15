package com.belong.dao;

import com.belong.model.Article;

import java.util.Map;

public interface ArticleMapper {
    void addArticle(Map map);

    void deleteArticle(Map map);

    void updateArticle(Map map);

    Article queryArticleByAid(Map map);

    void updataAgree(Map map);

    void updataDisagree(Map map);
}
