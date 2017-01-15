package com.belong.dao;

import com.belong.model.Movies;

public interface MoviesMapper {
    String getPath(int Vid);
    Movies getPic(int Vid);
    void views(int Vid);
}
