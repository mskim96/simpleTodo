package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentMovieLikeBinding
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import com.msk.simpletodo.presentation.viewModel.movie.adapter.GridItemDecoration
import com.msk.simpletodo.presentation.viewModel.movie.adapter.MovieSearchAdapter
import kotlinx.coroutines.flow.collectLatest

class MovieLikeFragment : Fragment() {

    private var _binding: FragmentMovieLikeBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { Firebase.auth }

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }
    private val movieLikeAdapter by lazy { MovieSearchAdapter { movie -> adapterOnClick(movie) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieLikeBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenStarted {
            (activity as MovieActivity).setBottomNavigation()
            movieViewModel.getMoviesByLike(uid = auth.uid.toString())
            movieViewModel.movieLikeData.collectLatest { data ->
                Log.d("TAG", "onCreateView: $data")
                movieLikeAdapter.submitList(data)
            }
        }

        binding.movieLikeRecycler.addItemDecoration(GridItemDecoration())
        binding.movieLikeRecycler.adapter = movieLikeAdapter

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