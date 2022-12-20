package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.movie.MovieLocalDatasource
import com.msk.simpletodo.data.datasource.movie.MovieRemoteDatasource
import com.msk.simpletodo.data.mapper.movieMapper
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDatasource: MovieRemoteDatasource,
    private val movieLocalDatasource: MovieLocalDatasource
) :
    MovieRepository {
    override suspend fun getRemoteMovies(page: Int): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getRemoteMovies(page).collect {
            emit(movieMapper(it))
        }
    }

    override suspend fun getLocalMovies(): Flow<List<Movie>> {
        return movieLocalDatasource.getLocalMovies()
    }

    override suspend fun insertMoviesLocal(movie: List<Movie>) {
        movieLocalDatasource.insertMoviesLocal(movie)
    }
}