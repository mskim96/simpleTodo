package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.movie.MovieEntity
import com.msk.simpletodo.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    /**
     * Get Movies from datasource
     */
    fun getMoviesByNewest(): Flow<List<Movie>>
    fun getMoviesByRating(rating: Int): Flow<List<Movie>>
    fun getMoviesByGenre(genre: String): Flow<List<Movie>>
    fun getMoviesByQuery(query: String): Flow<List<Movie>>

    /**
     * CRUD Method
     */
    suspend fun insertMoviesLocal(movie: List<MovieEntity>)
}