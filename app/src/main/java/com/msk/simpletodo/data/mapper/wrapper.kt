package com.msk.simpletodo.data.mapper

import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.domain.model.MovieWrapper

fun wrapper(title: String, movieList: List<Movie>): MovieWrapper {
    return MovieWrapper(title, movieList)
}