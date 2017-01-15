package com.belong.service;

import com.belong.dao.MoviesMapper;
import com.belong.dao.PageMapper;
import com.belong.dao.ReviewMapper;
import com.belong.model.Movies;
import com.belong.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by belong on 16-11-6.
 */
@Service
public class MoviesServiceImpl implements IMoviesService{

    @Autowired
    private MoviesMapper dao;

    @Autowired
    private PageMapper prodao;

    @Autowired
    private ReviewMapper rdao;

    @Override
    public String getPath(int Vid) {
        return dao.getPath(Vid);
    }


    @Override
    public Movies getPic(int Vid) {
        return dao.getPic(Vid);
    }

    @Override
    public ArrayList<Movies> search(Map map) {
        return prodao.search(map);
    }

    @Override
    public void views(int Vid) {
        dao.views(Vid);
    }

    @Override
    public void upload(Map map) {
        prodao.upload(map);
    }

    @Override
    public Review review(Map map) {
        return rdao.review(map);
    }


    @Override
    public ArrayList<Movies> getInfo(Map map) {
        return prodao.getInfo(map);
    }

}


