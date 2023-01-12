package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.MovieEntity

interface MovieLocalDatasource {
    /**
     * Get Movies from Local database
     * GET Method
     */
    suspend fun getMoviesByNewestLocal(): List<MovieEntity>

    /**
     * Get Movies by rating from Local database
     * GET Method
     */
    suspend fun getMoviesByRatingLocal(rating: Int): List<MovieEntity>

    /**
     * Get Movies by genres from Local database
     * GET Method
     */
    suspend fun getMoviesByGenreLocal(genres: String): List<MovieEntity>

    /**
     * Get Movies by Query from Local database
     * GET Method
     */
    suspend fun getMoviesByQueryLocal(query: String): List<MovieEntity>

    /**
     * CRUD Method
     * Insert movie data to local
     * Get Movie data from Remote
     */
    suspend fun insertMovies(movie: List<MovieEntity>)
}

