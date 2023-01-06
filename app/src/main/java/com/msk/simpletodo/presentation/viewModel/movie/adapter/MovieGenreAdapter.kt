package com.msk.simpletodo.presentation.viewModel.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.databinding.MovieGenreItemBinding

class MovieGenreAdapter(val data: Movie) :
    RecyclerView.Adapter<MovieGenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(val binding: MovieGenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: String) {
            binding.textView9.text = genre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            MovieGenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(data.genres[position])
    }

    override fun getItemCount(): Int {
        return data.genres.size
    }
}