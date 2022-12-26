package com.msk.simpletodo.data.api

import com.msk.simpletodo.data.model.movie.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/api/v2/list_movies.json?")
    suspend fun getNewMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("/api/v2/list_movies.json?minimum_rating=8")
    suspend fun getTopRatingMovie(): Response<MovieResponse>

}