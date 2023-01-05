package com.msk.simpletodo.data.datasource.movie

import com.msk.simpletodo.data.api.MovieInterface
import com.msk.simpletodo.data.model.movie.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDatasourceImpl @Inject constructor(
    private val movieInterface: MovieInterface,
) :
    MovieRemoteDatasource {
    override suspend fun getRemoteMovies(page: Int): Flow<List<MovieResponse.MovieData.MovieModel>> =
        flow {
            movieInterface.getNewMovies(page).body()?.data?.movies?.let { emit(it) }
        }

    override suspend fun getRatingMovies(): Flow<List<MovieResponse.MovieData.MovieModel>> = flow {
        movieInterface.getNewMovies(1).body()?.data?.movies?.let { emit(it) }
    }

    /**
     * TODO Test
     */
    override suspend fun getMoviesByNewest(
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>> = flow {
        movieInterface.getMoviesByNewest(page).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByGenre(
        genre: String,
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>> = flow {
        movieInterface.getMoviesByGenre(genre, page).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByRating(
        rating: Int,
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>> = flow {
        movieInterface.getMoviesByRating(rating, page).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByQuery(
        query: String,
        page: Int
    ): Flow<List<MovieResponse.MovieData.MovieModel>> = flow {
        movieInterface.getMoviesByQuery(query, page).body()?.data?.movies?.let { emit(it) }
    }
}