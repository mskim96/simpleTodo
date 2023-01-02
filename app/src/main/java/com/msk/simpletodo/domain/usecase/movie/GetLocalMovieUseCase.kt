package com.msk.simpletodo.domain.usecase.movie

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for get local movie data
 */
class GetLocalMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getMoviesFromLocal(): Flow<List<Movie>> {
        return repository.getMoviesFromLocal()
    }

    fun getLocalMoviesToGenre(genres: String): Flow<List<Movie>> {
        return repository.getLocalMoviesToGenre(genres)
    }

    fun getLocalMoviesToRating(): Flow<List<Movie>> {
        return repository.getLocalMoviesToRating()
    }
}