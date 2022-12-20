package com.msk.simpletodo.data.model.movie

import androidx.room.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movie: Movie)

    @Delete
    suspend fun deleteMovie(vararg movie: Movie)

    @Query("SELECT * FROM movie_table")
    suspend fun getAllMovies(): List<Movie>
}