package com.msk.simpletodo.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "movie_title")
    val title: String,

    @ColumnInfo(name = "movie_year")
    val year: Int,

    @ColumnInfo(name = "movie_rating")
    val rating: Float,

    @ColumnInfo(name = "movie_genres")
    val genres: List<String>,

    @ColumnInfo(name = "movie_description")
    val description: String,

    @ColumnInfo(name = "movie_coverImg-medium")
    val coverImgMedium: String,

    @ColumnInfo(name = "movie_coverImg-large")
    val coverImgLarge: String,
)