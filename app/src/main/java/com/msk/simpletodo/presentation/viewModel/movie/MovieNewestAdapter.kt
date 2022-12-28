package com.msk.simpletodo.presentation.viewModel.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.databinding.MovieItemListBinding


class MovieNewestAdapter :
    ListAdapter<Movie, MovieNewestAdapter.MovieViewHolder>(MovieDiffCallback()) {


    init {
        setHasStableIds(true)
    }

    private var listener: OnClickListener? = null

    interface OnClickListener {
        fun sendViewAndMovie(img: View, movie: Movie)
    }

    fun setViewAndMovieListener(listener: OnClickListener) {
        this.listener = listener
    }


    inner class MovieViewHolder(val binding: MovieItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentMovie: Movie) {
            Glide.with(binding.root).load(currentMovie.coverImg).override(130, 130)
                .into(binding.movieThumbnail)
            binding.movie = currentMovie
            binding.movieThumbnail.setOnClickListener {
                // after add callback
                listener?.sendViewAndMovie(binding.movieThumbnail, currentMovie)
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}