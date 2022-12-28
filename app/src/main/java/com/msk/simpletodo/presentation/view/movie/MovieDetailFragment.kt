package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentMovieDetailBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import kotlinx.coroutines.flow.collectLatest


class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        (activity as MovieActivity).hideBottomNavigation()

        lifecycleScope.launchWhenStarted {
            movieViewModel.movieDetailData.collectLatest {
                Glide.with(binding.root).load(it?.coverImg).into(binding.movieThumbnail)
                binding.movie = it
            }
        }

        (view?.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
        return view
    }
}