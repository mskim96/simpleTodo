package com.msk.simpletodo.data.model.movie

import com.google.gson.annotations.SerializedName

/**
 * Response type
 * data -> movies -> entity
 */
data class MovieResponse(
    @SerializedName("data")
    val data: MovieData
) {
    data class MovieData(
        val movies: List<MovieEntity>
    )
}