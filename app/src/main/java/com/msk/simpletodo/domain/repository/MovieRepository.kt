package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    /**
     * Get Movies from Remote datasource
     */
    suspend fun getMoviesFromRemote(page: Int): Flow<List<Movie>>
    suspend fun getTopRatingMoviesFromRemote(): Flow<List<Movie>>

    /**
     * Get Movies from Local datasource
     */
    fun getMoviesByGenreLocal(genres: String): Flow<List<Movie>>
    fun getLocalMoviesToRating(): Flow<List<Movie>>
    fun getMoviesFromLocal(): Flow<List<Movie>>
    fun getMoviesByQuery(query: String): Flow<List<Movie>>

    suspend fun insertMoviesLocal(movie: List<Movie>)

    /**
     * TODO : TEST
     */

    suspend fun getMoviesByNewestRemote(page: Int): Flow<List<Movie>>
    suspend fun getMoviesByGenreRemote(genre: String, page: Int): Flow<List<Movie>>
    suspend fun getMoviesByRatingRemote(rating: Int, page: Int): Flow<List<Movie>>
    suspend fun getMoviesByQueryRemote(query: String, page: Int): Flow<List<Movie>>
}