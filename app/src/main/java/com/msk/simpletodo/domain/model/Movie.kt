package com.msk.simpletodo.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val rating: Float,
    val genres: List<String>,
    val description: String,
    val coverImg: String,
) : Parcelable