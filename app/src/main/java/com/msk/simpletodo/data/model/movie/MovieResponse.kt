package com.msk.simpletodo.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("data")
    val data: MovieData
) {
    data class MovieData(
        val movies: List<MovieModel>
    ) {
        data class MovieModel(
            @SerializedName("id")
            val id: Int,

            @SerializedName("title")
            val title: String,

            @SerializedName("year")
            val year: Int,

            @SerializedName("rating")
            val rating: Float,

            @SerializedName("genres")
            val genres: List<String>,

            @SerializedName("summary")
            val description: String,

            @SerializedName("medium_cover_image")
            val coverImgMedium: String,

            @SerializedName("large_cover_image")
            val coverImgLarge: String,
        )
    }
}