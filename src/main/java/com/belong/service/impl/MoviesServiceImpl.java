package com.belong.service.impl;

import com.belong.dao.MoviesMapper;
import com.belong.dao.PageMapper;
import com.belong.dao.ReviewMapper;
import com.belong.model.Movies;
import com.belong.model.Review;
import com.belong.service.IMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by belong on 16-11-6.
 */
@Service
public class MoviesServiceImpl implements IMoviesService {

    @Autowired
    private MoviesMapper dao;

    @Autowired
    private PageMapper prodao;

    @Autowired
    private ReviewMapper rdao;

    @Override
    public int getVType(Map map) {
        return dao.getVType(map);
    }

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

    @Override
    public List<Movies> getTop20() {
        return dao.getTop20();
    }

    @Override
    public List<Movies> getVideosFromVname(Map map) {
        return dao.getVideosFromVname(map);
    }

    @Override
    public List<Movies> getVideosLately10() {
        return dao.getVideosLately10();
    }

    @Override
    public List<Movies> getVideosByVnameAndVinfo(Map map) {
        return dao.getVideosByVnameAndVinfo(map);
    }

    @Override
    public boolean updateVtypeByVid(Map map) {
        return dao.updateVtypeByVid(map);
    }
}


