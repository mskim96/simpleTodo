package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDatasource {
    /**
     * Get Method
     */
    suspend fun getLocalMovies(): Flow<List<Movie>>

    /**
     * CRUD Method
     */
    suspend fun insertMoviesLocal(movie: List<Movie>)
}

