package com.msk.simpletodo.data.repository

import android.util.Log
import com.msk.simpletodo.data.datasource.movie.MovieLocalDatasource
import com.msk.simpletodo.data.datasource.movie.MovieRemoteDatasource
import com.msk.simpletodo.data.mapper.mapper
import com.msk.simpletodo.data.mapper.reverseMap
import com.msk.simpletodo.data.model.movie.MovieEntity
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDatasource: MovieLocalDatasource,
    private val movieRemoteDatasource: MovieRemoteDatasource,
) : MovieRepository {

    override fun getMoviesByNewest(): Flow<List<Movie>> = flow {
        val moviesNewestInLocal = movieLocalDatasource.getMoviesByNewestLocal()
        if (moviesNewestInLocal.isNotEmpty() && moviesNewestInLocal.size > 20) {
            emit(mapper(moviesNewestInLocal.slice(0..19)))
            return@flow
        }
        val response = movieRemoteDatasource.getMoviesByNewest()
        response.collect { data ->
            insertMoviesLocal(data)
            emit(mapper(data))
        }
    }

    override fun getMoviesByRating(rating: Int): Flow<List<Movie>> = flow {
        val moviesRatingInLocal = movieLocalDatasource.getMoviesByRatingLocal(rating)
        if (moviesRatingInLocal.isNotEmpty() && moviesRatingInLocal.size > 20) {
            emit(mapper(moviesRatingInLocal))
            return@flow
        }
        val response = movieRemoteDatasource.getMoviesByRating(rating)
        response.collect { data ->
            insertMoviesLocal(data)
            emit(mapper(data))
        }
    }

    override fun getMoviesByGenre(genre: String): Flow<List<Movie>> = flow {
        val moviesGenreInLocal = movieLocalDatasource.getMoviesByGenreLocal("%${genre}%")
        if (moviesGenreInLocal.isNotEmpty() && moviesGenreInLocal.size > 20) {
            emit(mapper(moviesGenreInLocal))
            return@flow
        }
        val response = movieRemoteDatasource.getMoviesByGenre(genre)
        response.collect { data ->
            insertMoviesLocal(data)
            emit(mapper(data))
        }
    }

    override fun getMoviesByQuery(query: String): Flow<List<Movie>> = flow {
        val moviesQueryInLocal = movieLocalDatasource.getMoviesByQueryLocal("%${query}%")
        if (moviesQueryInLocal.isNotEmpty() && moviesQueryInLocal.size > 5) {
            emit(mapper(moviesQueryInLocal))
            return@flow
        }
        val response = movieRemoteDatasource.getMoviesByQuery(query)
        response.collect { data ->
            insertMoviesLocal(data)
            emit(mapper(data))
        }
    }

    override suspend fun getMoviesByLike(uid: String): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getMoviesByLike(uid).collect { data ->
            emit(mapper(data))
        }
    }

    override suspend fun insertMoviesLocal(movie: List<MovieEntity>) {
        movieLocalDatasource.insertMovies(movie)
    }

    override suspend fun saveMoviesByLike(uid: String, movie: Movie) {
        movieRemoteDatasource.saveMoviesByLike(uid, reverseMap(movie))
    }

    override suspend fun deleteMoviesByLike(uid: String, movie: Movie) {
        movieRemoteDatasource.deleteMoviesByLike(uid, reverseMap(movie))
    }
}