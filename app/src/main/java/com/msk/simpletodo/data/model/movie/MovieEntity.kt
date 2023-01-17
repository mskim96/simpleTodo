package com.msk.simpletodo.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("year")
    val year: Int = 0,

    @SerializedName("rating")
    val rating: Float = 0.0f,

    @SerializedName("genres")
    val genres: List<String> = listOf(),

    @SerializedName("summary")
    val description: String = "",

    @SerializedName("large_cover_image")
    val coverImg: String = "",

    val requestDate: Long = System.currentTimeMillis()
)