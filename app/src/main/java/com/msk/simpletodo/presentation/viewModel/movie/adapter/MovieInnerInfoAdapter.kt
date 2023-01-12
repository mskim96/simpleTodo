package com.msk.simpletodo.presentation.viewModel.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.databinding.MovieInfoItemBinding

class MovieInnerInfoAdapter(val data: List<String>) :
    RecyclerView.Adapter<MovieInnerInfoAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(val binding: MovieInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.infoTitle.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding =
            MovieInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}