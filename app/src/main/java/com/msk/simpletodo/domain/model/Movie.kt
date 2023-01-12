package com.msk.simpletodo.domain.model

data class Movie(
    val title: String,
    val year: Int,
    val rating: Float,
    val genres: List<String>,
    val description: String,
    val coverImg: String,
)