package com.msk.simpletodo.presentation.viewModel.movie.diff

import androidx.recyclerview.widget.DiffUtil
import com.msk.simpletodo.domain.model.Movie

class MovieItemDiffCallback() : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.coverImg == newItem.coverImg
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}