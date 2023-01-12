package com.msk.simpletodo.domain.usecase.movie

import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.domain.model.MovieGenre
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for get movie data
 */
class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getMoviesByNewest(): Flow<List<Movie>> {
        return repository.getMoviesByNewest()
    }

    fun getMoviesByRating(rating: Int): Flow<List<Movie>> {
        return repository.getMoviesByRating(rating)
    }

    fun getMoviesByGenre(genre: String): Flow<List<Movie>> {
        return repository.getMoviesByGenre(genre)
    }

    fun getMoviesByQuery(query: String): Flow<List<Movie>> {
        return repository.getMoviesByQuery(query)
    }
}