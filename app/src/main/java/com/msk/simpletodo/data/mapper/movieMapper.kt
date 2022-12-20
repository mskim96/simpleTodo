package com.msk.simpletodo.data.mapper

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.data.model.movie.MovieResponse
import kotlin.math.ceil

fun movieMapper(movieResponseList: List<MovieResponse.MovieData.MovieModel>): List<Movie> {
    return movieResponseList.map {
        Movie(
            id = it.id,
            title = it.title,
            year = it.year,
            rating = ceil(it.rating),
            genres = it.genres,
            description = it.description,
            coverImg = it.coverImg
        )
    }
}