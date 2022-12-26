package com.msk.simpletodo.domain.model

import com.msk.simpletodo.data.model.movie.Movie

data class MovieWithCategory(
    val category: String,
    val movie: List<Movie>
)