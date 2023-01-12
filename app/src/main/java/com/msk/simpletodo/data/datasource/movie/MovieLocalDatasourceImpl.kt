package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.MovieDao
import com.msk.simpletodo.data.model.movie.MovieEntity
import javax.inject.Inject

class MovieLocalDatasourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDatasource {

    override suspend fun getMoviesByNewestLocal(): List<MovieEntity> {
        return movieDao.getMoviesByNewestLocal()
    }

    override suspend fun getMoviesByRatingLocal(rating: Int): List<MovieEntity> {
        return movieDao.getMoviesByRatingLocal(rating)
    }

    override suspend fun getMoviesByGenreLocal(genres: String): List<MovieEntity> {
        // parameter genres convert [string] to [list]
        // It's because the genre that MovieEntity has is List format
        return movieDao.getMoviesByGenreLocal(genres = listOf(genres))
    }

    override suspend fun getMoviesByQueryLocal(query: String): List<MovieEntity> {
        return movieDao.getMoviesByQueryLocal(query = query)
    }

    override suspend fun insertMovies(movie: List<MovieEntity>) {
        movieDao.insertMovies(movie = movie.toTypedArray())
    }
}