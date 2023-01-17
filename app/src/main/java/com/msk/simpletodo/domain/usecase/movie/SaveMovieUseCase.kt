package com.msk.simpletodo.domain.usecase.movie

import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import javax.inject.Inject

class SaveMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun saveMoviesByLike(uid: String, movie: Movie) {
        return repository.saveMoviesByLike(uid, movie)
    }

    suspend fun deleteMoviesByLike(uid: String, movie: Movie) {
        return repository.deleteMoviesByLike(uid, movie)
    }
}