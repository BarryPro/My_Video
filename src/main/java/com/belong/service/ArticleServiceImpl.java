package com.belong.service;

import com.belong.dao.ArticleMapper;
import com.belong.dao.PageMapper;
import com.belong.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by belong on 2017/1/10.
 */
@Service
public class ArticleServiceImpl implements IArticleService{
    @Autowired
    private PageMapper pdao;

    @Autowired
    private ArticleMapper dao;

    @Override
    public void addArticle(Map map) {
        dao.addArticle(map);
    }

    @Override
    public ArrayList<Article> queryArticle(Map map) {
        return pdao.query(map);
    }

    @Override
    public void deleteArticle(Map map) {
        dao.deleteArticle(map);
    }

    @Override
    public void updateArticle(Map map) {
        dao.updateArticle(map);
    }

    @Override
    public Article queryArticleByAid(Map map) {
        return dao.queryArticleByAid(map);
    }

    @Override
    public void updateAgree(Map map) {
        dao.updataAgree(map);
    }

    @Override
    public void updataDisagree(Map map) {
        dao.updataDisagree(map);
    }

}
