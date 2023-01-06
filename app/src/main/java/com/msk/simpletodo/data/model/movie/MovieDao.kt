package com.msk.simpletodo.data.model.movie

import androidx.room.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movie: Movie)

    @Delete
    suspend fun deleteMovie(vararg movie: Movie)

    @Query("SELECT * FROM movie_table ORDER BY id ASC")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE movie_rating > 8.0 ORDER BY id DESC")
    suspend fun getMoviesToRating(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE movie_genres LIKE :genres ORDER BY id DESC")
    suspend fun getMoviesToGenre(genres: List<String>): List<Movie>

    @Query("SELECT * FROM movie_table WHERE movie_title LIKE :query ORDER BY id DESC")
    suspend fun getMoviesByQuery(query: String): List<Movie>
}