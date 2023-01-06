package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.databinding.FragmentMovieDetailRelBinding
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import com.msk.simpletodo.presentation.viewModel.movie.adapter.GridItemDecoration
import com.msk.simpletodo.presentation.viewModel.movie.adapter.MovieInnerRelAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieDetailRelFragment : Fragment() {

    private var _binding: FragmentMovieDetailRelBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel by lazy {
        ViewModelProvider(requireActivity())[MovieViewModel::class.java]
    }
    private val movieInnerRelAdapter by lazy {
        MovieInnerRelAdapter { movie -> adapterOnClick(movie) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailRelBinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenStarted {
            launch {
                movieViewModel.movieDetailData.collectLatest {
                    if (it != null) {
                        movieViewModel.getMoviesByGenreRelated(it.genres[0])
                    }
                }
            }

            launch {
                movieViewModel.movieRelData.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {}
                        is UiState.Success -> movieInnerRelAdapter.submitList(state.data)
                        is UiState.Error -> throw IllegalStateException()
                    }
                }
            }
        }
        binding.movieRelRecycler.adapter = movieInnerRelAdapter
        binding.movieRelRecycler.addItemDecoration(GridItemDecoration())

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun adapterOnClick(movie: Movie) {
        movieViewModel.sendMovieDataForDetail(movie)
        (activity as MovieActivity).navDetailFragment()
    }
}