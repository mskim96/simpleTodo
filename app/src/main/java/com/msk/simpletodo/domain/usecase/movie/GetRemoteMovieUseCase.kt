package com.msk.simpletodo.domain.usecase.movie

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.model.MovieGenre
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for get remote movie data
 */
class GetRemoteMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun execute(page: Int): Flow<List<Movie>> {
        return repository.getMoviesFromRemote(page)
    }

    suspend fun getTopRatingMovies(): Flow<List<Movie>> {
        return repository.getLocalMoviesToRating()
    }

    suspend fun getMoviesByNewestRemote(page: Int): Flow<List<Movie>> {
        return repository.getMoviesByNewestRemote(page)
    }

    // TODO Test

    suspend fun getMoviesByRatingRemote(rating: Int, page: Int): Flow<List<Movie>> {
        return repository.getMoviesByRatingRemote(rating, page)
    }

    suspend fun getMoviesByGenreRemote(genre: MovieGenre, page: Int): Flow<List<Movie>> =
        when (genre) {
            is MovieGenre.Action -> repository.getMoviesByGenreRemote("Action", page)
            is MovieGenre.Drama -> repository.getMoviesByGenreRemote("Drama", page)
            is MovieGenre.Comedy -> repository.getMoviesByGenreRemote("Comedy", page)
            is MovieGenre.Animation -> repository.getMoviesByGenreRemote("Animation", page)
            is MovieGenre.Thriller -> repository.getMoviesByGenreRemote("Thriller", page)
            is MovieGenre.Adventure -> repository.getMoviesByGenreRemote("Adventure", page)
        }

    suspend fun getMoviesByQueryRemote(query: String, page: Int): Flow<List<Movie>> {
        return repository.getMoviesByQueryRemote(query, page)
    }
}