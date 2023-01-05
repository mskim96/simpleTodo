package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDatasource {
    /**
     * TODO : This method will deprecated later
     */
    suspend fun getRemoteMovies(page: Int): Flow<List<MovieResponse.MovieData.MovieModel>>
    suspend fun getRatingMovies(): Flow<List<MovieResponse.MovieData.MovieModel>>

    /**
     * Get movie data by newest
     */
    suspend fun getMoviesByNewest(
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>>

    /**
     * get movie data by genre
     */
    suspend fun getMoviesByGenre(
        genre: String,
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>>

    /**
     * get movie data by rating
     */
    suspend fun getMoviesByRating(
        rating: Int,
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>>

    /**
     * Search movie by query(keyword)
     */
    suspend fun getMoviesByQuery(
        query: String,
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>>
}