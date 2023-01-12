package com.msk.simpletodo.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("year")
    val year: Int,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("genres")
    val genres: List<String>,

    @SerializedName("summary")
    val description: String,

    @SerializedName("large_cover_image")
    val coverImg: String,

    val requestDate: Long = System.currentTimeMillis()
)