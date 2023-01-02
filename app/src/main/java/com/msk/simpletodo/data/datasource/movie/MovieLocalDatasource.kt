package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.Movie

interface MovieLocalDatasource {
    /**
     * Get Movies from Local database
     * GET Method
     */
    suspend fun getLocalMovies(): List<Movie>

    /**
     * Get Movies by rating from Local database
     * GET Method
     */
    suspend fun getLocalMoviesToRating(): List<Movie>

    /**
     * Get Movies by genres from Local database
     * GET Method
     */
    suspend fun getLocalMoviesToGenre(genres: String): List<Movie>

    /**
     * CRUD Method
     * Insert movie data to local
     * Get Movie data from Remote
     */
    suspend fun insertMoviesLocal(movie: List<Movie>)
}

