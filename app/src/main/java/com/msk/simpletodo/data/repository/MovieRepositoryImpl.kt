package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.movie.MovieLocalDatasource
import com.msk.simpletodo.data.datasource.movie.MovieRemoteDatasource
import com.msk.simpletodo.data.mapper.movieMapper
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
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
            emit(movieMapper(it))
        }
    }

    override suspend fun getTopRatingMoviesFromRemote(): Flow<List<Movie>> = flow {
        movieRemoteDatasource.getRatingMovies().collect {
            emit(movieMapper(it))
        }
    }

    /**
     * get movie data from Local
     */
    override fun getMoviesFromLocal(): Flow<List<Movie>> = flow {
        emit(movieLocalDatasource.getLocalMovies())
    }

    override fun getLocalMoviesToGenre(genres: String): Flow<List<Movie>> = flow {
        emit(movieLocalDatasource.getLocalMoviesToGenre("%${genres}%"))
    }

    override fun getLocalMoviesToRating(): Flow<List<Movie>> = flow {
        emit(movieLocalDatasource.getLocalMoviesToRating())
    }

    override suspend fun insertMoviesLocal(movie: List<Movie>) {
        movieLocalDatasource.insertMoviesLocal(movie)
    }
}