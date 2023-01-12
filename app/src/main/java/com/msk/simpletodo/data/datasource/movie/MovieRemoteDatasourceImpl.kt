package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.api.MovieApiInterface
import com.msk.simpletodo.data.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDatasourceImpl @Inject constructor(
    private val movieApiInterface: MovieApiInterface,
) : MovieRemoteDatasource {

    override suspend fun getMoviesByNewest(
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByNewest().body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByGenre(
        genre: String,
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByGenre(genre).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByRating(
        rating: Int,
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByRating(rating).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByQuery(
        query: String,
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByQuery(query).body()?.data?.movies?.let { emit(it) }
    }
}