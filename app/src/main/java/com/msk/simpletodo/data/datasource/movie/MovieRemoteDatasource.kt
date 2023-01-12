package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDatasource {
    /**
     * Get movie data by newest
     */
    suspend fun getMoviesByNewest(
    ): Flow<List<MovieEntity>>

    /**
     * get movie data by genre
     */
    suspend fun getMoviesByGenre(
        genre: String,
    ): Flow<List<MovieEntity>>

    /**
     * get movie data by rating
     */
    suspend fun getMoviesByRating(
        rating: Int,
    ): Flow<List<MovieEntity>>

    /**
     * Search movie by query(keyword)
     */
    suspend fun getMoviesByQuery(
        query: String,
    ): Flow<List<MovieEntity>>
}