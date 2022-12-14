package com.msk.simpletodo.presentation.viewModel.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msk.simpletodo.databinding.MovieItemMovieBinding
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.presentation.viewModel.movie.diff.MovieItemDiffCallback


class MovieInnerRelAdapter(private val onClick: (Movie) -> Unit) :
    ListAdapter<Movie, MovieInnerRelAdapter.MovieViewHolder>(MovieItemDiffCallback()) {

    inner class MovieViewHolder(val binding: MovieItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentMovie: Movie) {
            Glide.with(binding.root).load(currentMovie.coverImg).override(130, 130)
                .skipMemoryCache(false)
                .into(binding.movieThumbnail)
            binding.movie = currentMovie
            binding.movieThumbnail.setOnClickListener {
                // after add callback
                onClick(currentMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}