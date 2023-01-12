package com.msk.simpletodo.data.mapper

import com.msk.simpletodo.data.model.movie.MovieEntity
import com.msk.simpletodo.domain.model.Movie
import kotlin.math.ceil

fun mapper(movieEntity: List<MovieEntity>): List<Movie> {
    return movieEntity.map {
        Movie(
            title = it.title,
            year = it.year,
            rating = ceil(it.rating),
            genres = it.genres,
            description = it.description,
            coverImg = it.coverImg,
        )
    }
}