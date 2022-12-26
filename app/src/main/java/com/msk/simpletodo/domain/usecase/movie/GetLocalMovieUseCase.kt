package com.msk.simpletodo.domain.usecase.movie

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for get local movie data
 */
class GetLocalMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun execute(): Flow<List<Movie>> {
        return repository.getLocalMovies()
    }

    suspend fun getMoviesToGenre(genres: String): Flow<List<Movie>> {
        return repository.getMoviesToGenre(genres)
    }

    suspend fun getMoviesToRating(): Flow<List<Movie>> {
        return repository.getMoviesToRating()
    }
}