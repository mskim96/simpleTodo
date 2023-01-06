package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.data.model.movie.MovieDao
import javax.inject.Inject

class MovieLocalDatasourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDatasource {

    override suspend fun getLocalMovies(): List<Movie> {
        return movieDao.getAllMovies()
    }

    override suspend fun getLocalMoviesToRating(): List<Movie> {
        return movieDao.getMoviesToRating()
    }

    override suspend fun getLocalMoviesToGenre(genres: String): List<Movie> {
        // parameter genres convert [string] to [list]
        // It's because the genre that MovieEntity has is List format
        return movieDao.getMoviesToGenre(listOf(genres))
    }

    override suspend fun getLocalMoviesByQuery(query: String): List<Movie> {
        return movieDao.getMoviesByQuery(query)
    }

    override suspend fun insertMoviesLocal(movie: List<Movie>) {
        movieDao.insertMovies(movie = movie.toTypedArray())
    }
}