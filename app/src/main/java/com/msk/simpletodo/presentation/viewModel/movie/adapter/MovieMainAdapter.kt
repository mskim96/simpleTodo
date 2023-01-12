package com.msk.simpletodo.presentation.viewModel.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.databinding.MovieItemSectionBinding
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.domain.model.MovieWrapper
import com.msk.simpletodo.presentation.viewModel.movie.diff.MovieDiffCallback


class MovieMainAdapter(private val onClick: (Movie) -> Unit) :
    ListAdapter<MovieWrapper, MovieMainAdapter.MovieViewHolder>(MovieDiffCallback()) {

    inner class MovieViewHolder(val binding: MovieItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieWrapper: MovieWrapper) {
            binding.movieMainSection.text = movieWrapper.title
            binding.movieInnerRecycler.adapter =
                MovieInnerAdapter(movieWrapper.movies) { movie -> onClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}