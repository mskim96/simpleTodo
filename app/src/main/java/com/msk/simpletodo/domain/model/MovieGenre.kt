package com.msk.simpletodo.domain.model

sealed class MovieGenre {
    object Drama : MovieGenre() //

    object Action : MovieGenre() //

    object Comedy : MovieGenre() //

    object Thriller : MovieGenre()

    object Animation : MovieGenre()

    object Adventure : MovieGenre()
}