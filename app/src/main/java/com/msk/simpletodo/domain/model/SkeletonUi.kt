package com.msk.simpletodo.domain.model

class SkeletonUi {
    companion object {
        val movie = Movie(1, "", 0, 0.0f, listOf(), "", "")
        val skeletonMovie = listOf<MovieWrapper>(
            MovieWrapper("Newest", listOf(movie, movie, movie)),
            MovieWrapper("Top Rating Movie", listOf(movie, movie, movie)),
            MovieWrapper("Action", listOf(movie, movie, movie)),
            MovieWrapper("Animation", listOf(movie, movie, movie)),
            MovieWrapper("Adventure", listOf(movie, movie, movie)),
            MovieWrapper("Comedy", listOf(movie, movie, movie)),
            MovieWrapper("Drama", listOf(movie, movie, movie)),
        )
    }
}