package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentMovieMainBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import kotlinx.coroutines.flow.collectLatest

class MovieMainFragment : BaseFragment<FragmentMovieMainBinding>(R.layout.fragment_movie_main) {

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            movieViewModel.movieLocalData.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> state
                    // send submit list for recycler view
                    is UiState.Success -> Log.d("TAG", "onCreateView: ${state.data}")
                }
            }
        }


        return view
    }
}