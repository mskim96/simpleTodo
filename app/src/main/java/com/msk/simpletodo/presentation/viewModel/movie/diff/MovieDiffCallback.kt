package com.msk.simpletodo.presentation.viewModel.movie.diff

import androidx.recyclerview.widget.DiffUtil
import com.msk.simpletodo.domain.model.MovieWrapper

class MovieDiffCallback() : DiffUtil.ItemCallback<MovieWrapper>() {
    override fun areItemsTheSame(oldItem: MovieWrapper, newItem: MovieWrapper): Boolean =
        oldItem.movies == newItem.movies

    override fun areContentsTheSame(oldItem: MovieWrapper, newItem: MovieWrapper): Boolean =
        oldItem == newItem
}