package com.msk.simpletodo.data.datasource.movie

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.msk.simpletodo.data.api.MovieApiInterface
import com.msk.simpletodo.data.model.movie.MovieEntity
import com.msk.simpletodo.presentation.util.toSuspendable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRemoteDatasourceImpl @Inject constructor(
    private val movieApiInterface: MovieApiInterface,
    private val fireStore: FirebaseFirestore
) : MovieRemoteDatasource {

    override suspend fun getMoviesByNewest(
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByNewest().body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByGenre(
        genre: String,
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByGenre(genre).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByRating(
        rating: Int,
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByRating(rating).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByQuery(
        query: String,
    ): Flow<List<MovieEntity>> = flow {
        movieApiInterface.getMoviesByQuery(query).body()?.data?.movies?.let { emit(it) }
    }

    override suspend fun getMoviesByLike(uid: String): Flow<List<MovieEntity>> = flow {
        val movies = fireStore.collection("uid").document(uid).collection("movies")
            .get().toSuspendable().toObjects(MovieEntity::class.java)
        emit(movies)
    }

    override suspend fun saveMoviesByLike(uid: String, movie: MovieEntity) {
        withContext(Dispatchers.IO) {
            fireStore.collection("uid").document(uid).collection("movies")
                .document(movie.id.toString()).set(movie).toSuspendable()
        }
    }

    override suspend fun deleteMoviesByLike(uid: String, movie: MovieEntity) {
        withContext(Dispatchers.IO) {
            fireStore.collection("uid").document(uid).collection("movies")
                .document(movie.id.toString()).delete().toSuspendable()
        }
    }
}