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

    override suspend fun getMoviesToGenre(genres: String): Flow<List<Movie>> = flow {
        emit(movieDao.getMoviesToGenre(listOf(genres)))
    }

    override suspend fun getMoviesToRating(): Flow<List<Movie>> = flow {
        emit(movieDao.getMoviesToRating())
    }

    /**
     * CRUD Method
     */
    override suspend fun insertMoviesLocal(movie: List<Movie>) {
        movieDao.insertMovies(movie = movie.toTypedArray())
    }
}