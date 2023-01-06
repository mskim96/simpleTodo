package com.msk.simpletodo.domain.usecase.movie

import android.util.Log
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.model.MovieGenre
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

/**
 * Use case for get local movie data
 */
class GetLocalMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getMoviesFromLocal(): Flow<List<Movie>> {
        return repository.getMoviesFromLocal()
    }

    fun getMoviesByGenreLocal(genres: MovieGenre): Flow<List<Movie>> =
        when (genres) {
            is MovieGenre.Action -> repository.getMoviesByGenreLocal("Action")
            is MovieGenre.Drama -> repository.getMoviesByGenreLocal("Drama")
            is MovieGenre.Comedy -> repository.getMoviesByGenreLocal("Comedy")
            is MovieGenre.Animation -> repository.getMoviesByGenreLocal("Animation")
            is MovieGenre.Thriller -> repository.getMoviesByGenreLocal("Thriller")
            is MovieGenre.Adventure -> repository.getMoviesByGenreLocal("Adventure")
        }

    fun getMoviesByGenreRelated(genres: String): Flow<List<Movie>> {
        return repository.getMoviesByGenreLocal(genres)
    }

    fun getMoviesByRatingLocal(): Flow<List<Movie>> {
        return repository.getLocalMoviesToRating()
    }

    fun getMoviesByQuery(query: String): Flow<List<Movie>> {
        return repository.getMoviesByQuery(query)
    }
}