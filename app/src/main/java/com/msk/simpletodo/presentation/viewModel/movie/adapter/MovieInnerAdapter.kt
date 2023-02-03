package com.msk.simpletodo.presentation.viewModel.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msk.simpletodo.databinding.MovieItemMovieBinding
import com.msk.simpletodo.domain.model.Movie

class MovieInnerAdapter(
    private val movieList: List<Movie>,
    private val onClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<MovieInnerAdapter.MovieInnerViewHolder>() {

    inner class MovieInnerViewHolder(val binding: MovieItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.root).load(movie.coverImg).override(130, 130)
                .skipMemoryCache(false)
                .into(binding.movieThumbnail)
            binding.movie = movie
            binding.movieThumbnail.setOnClickListener {
                onClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieInnerViewHolder {
        return MovieInnerViewHolder(
            MovieItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieInnerViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}