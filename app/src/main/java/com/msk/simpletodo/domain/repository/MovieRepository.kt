package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getRemoteMovies(page: Int): Flow<List<Movie>>


    suspend fun getLocalMovies(): Flow<List<Movie>>
    suspend fun insertMoviesLocal(movie: List<Movie>)
}