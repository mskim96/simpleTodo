package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.data.model.movie.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieLocalDatasourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDatasource {

    /**
     * Query
     */
    override suspend fun getLocalMovies(): Flow<List<Movie>> = flow {
        emit(movieDao.getAllMovies())
    }

    /**
     * CRUD Method
     */
    override suspend fun insertMoviesLocal(movie: List<Movie>) {
        movieDao.insertMovies(movie = movie.toTypedArray())
    }
}