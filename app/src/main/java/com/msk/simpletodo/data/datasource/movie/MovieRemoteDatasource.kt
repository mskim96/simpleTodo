package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.model.movie.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDatasource {
    suspend fun getRemoteMovies(page: Int): Flow<List<MovieResponse.MovieData.MovieModel>>

}