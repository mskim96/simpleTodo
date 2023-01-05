package com.msk.simpletodo.presentation.viewModel.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.databinding.MovieItemListBinding
import com.msk.simpletodo.presentation.viewModel.movie.diff.MovieDiffCallback


class MovieThrillerAdapter(private var onClick: (Movie) -> Unit) :
    ListAdapter<Movie, MovieThrillerAdapter.MovieViewHolder>(MovieDiffCallback()) {

    inner class MovieViewHolder(val binding: MovieItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentMovie: Movie) {
            Glide.with(binding.root).load(currentMovie.coverImg).override(130, 130)
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
            MovieItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}