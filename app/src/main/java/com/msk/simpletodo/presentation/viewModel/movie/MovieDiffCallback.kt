package com.msk.simpletodo.presentation.viewModel.movie

import androidx.recyclerview.widget.DiffUtil
import com.msk.simpletodo.data.model.movie.Movie

class MovieDiffCallback() : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}