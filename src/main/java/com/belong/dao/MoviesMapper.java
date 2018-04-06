package com.belong.dao;

import com.belong.model.Movies;

import java.util.List;
import java.util.Map;

public interface MoviesMapper {
    int getVType(Map map);
    String getPath(int Vid);
    Movies getPic(int Vid);
    void views(int Vid);
    List<Movies> getTop20();
    List<Movies> getVideosFromVname(Map map);
}
