package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.movie.MovieLocalDatasource
import com.msk.simpletodo.data.datasource.movie.MovieRemoteDatasource
import com.msk.simpletodo.data.mapper.movieDataMapper
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDatasource: MovieRemoteDatasource,
    private val movieLocalDatasource: MovieLocalDatasource
) :
    MovieRepository {
    /**
     * get movie data from Remote
     */
    override suspend fun getMoviesFromRemote(page: Int): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getRemoteMovies(page).collect {
            emit(movieDataMapper(it))
        }
    }

    override suspend fun getTopRatingMoviesFromRemote(): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getRatingMovies().collect {
            emit(movieDataMapper(it))
        }
    }

    /**
     * get movie data from Local
     */
    override fun getMoviesFromLocal(): Flow<List<Movie>> = flow {
        emit(movieLocalDatasource.getLocalMovies())
    }

    override fun getMoviesByQuery(query: String): Flow<List<Movie>> = flow {
        emit(movieLocalDatasource.getLocalMoviesByQuery("%${query}%"))
    }

    override fun getMoviesByGenreLocal(genres: String): Flow<List<Movie>> = flow {
        emit(movieLocalDatasource.getLocalMoviesToGenre("%${genres}%"))
    }

    override fun getLocalMoviesToRating(): Flow<List<Movie>> = flow {
        emit(movieLocalDatasource.getLocalMoviesToRating())
    }

    override suspend fun insertMoviesLocal(movie: List<Movie>) {
        movieLocalDatasource.insertMoviesLocal(movie)
    }

    /**
     * TODO : TEST
     */

    override suspend fun getMoviesByNewestRemote(
        page: Int
    ): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getMoviesByNewest(page).collect {
            emit(movieDataMapper(it))
        }
    }

    override suspend fun getMoviesByGenreRemote(
        genre: String, page: Int
    ): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getMoviesByGenre(genre, page).collect {
            emit(movieDataMapper(it))
        }
    }

    override suspend fun getMoviesByRatingRemote(
        rating: Int, page: Int
    ): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getMoviesByRating(rating, page).collect {
            emit(movieDataMapper(it))
        }
    }

    override suspend fun getMoviesByQueryRemote(
        query: String, page: Int
    ): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getMoviesByQuery(query, page).collect {
            emit(movieDataMapper(it))
        }
    }
}