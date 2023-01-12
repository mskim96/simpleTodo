package com.msk.simpletodo.presentation.view.movie

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentMovieMainBinding
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.domain.model.SkeletonUi
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import com.msk.simpletodo.presentation.viewModel.movie.adapter.MovieMainAdapter
import kotlinx.coroutines.flow.collectLatest


class MovieMainFragment : BaseFragment<FragmentMovieMainBinding>(R.layout.fragment_movie_main) {

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }
    private val movieAdapter by lazy { MovieMainAdapter { movie -> adapterOnClick(movie) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        (activity as MovieActivity).setBottomNavigation()

        lifecycleScope.launchWhenStarted {
            movieViewModel.movieData.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        if (state.data.isEmpty()) movieAdapter.submitList(SkeletonUi.skeletonMovie)
                        else movieAdapter.submitList(state.data)
                    }
                    is UiState.Error -> {}
                }
            }
        }

        bind {
            binding.movieMainRecycler.adapter = movieAdapter
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.movieMainScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->

                if (scrollY > 1100) {
                    binding.movieMainHeader.setBackgroundColor(resources.getColor(R.color.black))
                }
                if (oldScrollY > scrollY && scrollY < 1100) {
                    binding.movieMainHeader.setBackgroundColor(resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
        }
        return view
    }

    private fun adapterOnClick(movie: Movie) {
        movieViewModel.sendMovieDataForDetail(movie)
        (activity as MovieActivity).navDetailFragment()
    }
}

