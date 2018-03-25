package com.belong.dao;

import com.belong.model.Movies;

import java.util.List;

public interface MoviesMapper {
    String getPath(int Vid);
    Movies getPic(int Vid);
    void views(int Vid);
    List<Movies> getTop20();
}
