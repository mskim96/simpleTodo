package com.msk.simpletodo.data.model.movie

import androidx.room.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(vararg movie: MovieEntity)

    @Query("SELECT * FROM movie_table")
    suspend fun getMoviesByNewestLocal(): List<MovieEntity>

    @Query("SELECT * FROM movie_table WHERE rating > :rating")
    suspend fun getMoviesByRatingLocal(rating: Int): List<MovieEntity>

    @Query("SELECT * FROM movie_table WHERE genres LIKE :genres")
    suspend fun getMoviesByGenreLocal(genres: List<String>): List<MovieEntity>

    @Query("SELECT * FROM movie_table WHERE title LIKE :query")
    suspend fun getMoviesByQueryLocal(query: String): List<MovieEntity>
}