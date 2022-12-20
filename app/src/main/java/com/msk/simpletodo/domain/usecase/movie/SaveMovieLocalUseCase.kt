package com.msk.simpletodo.domain.usecase.movie

import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import javax.inject.Inject

class SaveMovieLocalUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun execute(movie: List<Movie>) {
        movieRepository.insertMoviesLocal(movie)
    }
}