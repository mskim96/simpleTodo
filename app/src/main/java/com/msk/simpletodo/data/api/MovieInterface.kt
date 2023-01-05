package com.msk.simpletodo.data.api

import com.msk.simpletodo.data.model.movie.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {
    /**
     * Get new movie data
     * This query get page parameter.
     * TODO : This method will deprecated later
     */
    @GET("/api/v2/list_movies.json?")
    suspend fun getNewMovies(@Query("page") page: Int): Response<MovieResponse>


    /**
     * Get movie data by newest
     */
    @GET("/api/v2/list_movies.json?sort_by=date_added")
    suspend fun getMoviesByNewest(@Query("page") page: Int): Response<MovieResponse>

    /**
     * Get movie data by genre
     */
    @GET("/api/v2/list_movies.json?genre=comedy")
    suspend fun getMoviesByGenre(
        @Query("genre") genre: String,
        @Query("page") page: Int
    ): Response<MovieResponse>


    /**
     * Get movie data by rating
     */
    @GET("/api/v2/list_movies.json?minimum_rating=8")
    suspend fun getMoviesByRating(
        @Query("rating") rating: Int,
        @Query("page") page: Int
    ): Response<MovieResponse>


    /**
     * Get movie data by keyword.
     * parameter query : search keyword
     */
    @GET("/api/v2/list_movies.json?")
    suspend fun getMoviesByQuery(
        @Query("query_term") query_term: String,
        @Query("page") page: Int
    ): Response<MovieResponse>
}