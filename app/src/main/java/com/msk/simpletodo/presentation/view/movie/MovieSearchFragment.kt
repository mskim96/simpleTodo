package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.databinding.FragmentMovieSearchBinding
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import com.msk.simpletodo.presentation.viewModel.movie.adapter.GridItemDecoration
import com.msk.simpletodo.presentation.viewModel.movie.adapter.MovieSearchAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieSearchFragment : Fragment() {

    private var _binding: FragmentMovieSearchBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }
    private val movieSearchAdapter by lazy { MovieSearchAdapter { movie -> adapterOnClick(movie) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieSearchBinding.inflate(inflater, container, false)

        val movieSearch = binding.movieSearch
        val textClearButton = binding.textClearButton

        lifecycleScope.launchWhenStarted {
            (activity as MovieActivity).setBottomNavigation()
            movieSearch.setText(movieViewModel.movieSearchQuery.value)
            launch {
                movieViewModel.movieSearchResult.collectLatest { data ->
                    movieSearchAdapter.submitList(data)
                }
            }
        }

        movieSearch.doAfterTextChanged {
            if (movieSearch.text.isNullOrBlank()) {
                textClearButton.visibility = View.GONE
            } else {
                lifecycleScope.launch { movieViewModel.movieSearchQuery.emit(it.toString()) }
                textClearButton.visibility = View.VISIBLE
            }
        }

        textClearButton.setOnClickListener {
            movieSearch.text = null
            lifecycleScope.launch {
                movieViewModel.movieSearchQuery.emit("")
                movieSearchAdapter.submitList(null)
            }
        }

        binding.movieSearchRecycler.addItemDecoration(GridItemDecoration())
        binding.movieSearchRecycler.adapter = movieSearchAdapter

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