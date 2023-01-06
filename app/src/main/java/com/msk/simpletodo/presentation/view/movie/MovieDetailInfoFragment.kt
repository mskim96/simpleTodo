package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.databinding.FragmentMovieDetailInfoBinding
import com.msk.simpletodo.presentation.viewModel.movie.adapter.MovieInnerInfoAdapter

class MovieDetailInfoFragment : Fragment() {

    private var _binding: FragmentMovieDetailInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailInfoBinding.inflate(inflater, container, false)
        val dummy =
            listOf("Stories 1", "Stories 2", "Stories 3", "Stories 4")
        binding.movieInfoRecycler.adapter = MovieInnerInfoAdapter(dummy)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}