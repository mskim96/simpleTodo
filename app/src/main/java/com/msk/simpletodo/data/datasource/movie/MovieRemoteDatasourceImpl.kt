package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.api.ApiInterface
import com.msk.simpletodo.data.model.movie.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDatasourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) :
    MovieRemoteDatasource {
    override suspend fun getRemoteMovies(page: Int): Flow<List<MovieResponse.MovieData.MovieModel>> =
        flow {
            apiInterface.getNewMovies(page).body()?.data?.movies?.let { emit(it) }
        }

    override suspend fun getRatingMovies(): Flow<List<MovieResponse.MovieData.MovieModel>> = flow {
        apiInterface.getTopRatingMovie().body()?.data?.movies?.let { emit(it) }
    }

}