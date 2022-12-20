package com.msk.simpletodo.domain.usecase.movie

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for get local movie data
 */
class GetLocalMovieUseCase @Inject constructor(private val repository: MovieRepository){
    suspend fun execute(): Flow<List<Movie>> {
        return repository.getLocalMovies()
    }
}