package com.msk.simpletodo.presentation.view.movie

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.databinding.FragmentMovieMainBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.movie.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MovieMainFragment : BaseFragment<FragmentMovieMainBinding>(R.layout.fragment_movie_main) {

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }

    private val movieNewestAdapter by lazy { MovieNewestAdapter() }
    private val movieTopRatingAdapter by lazy { MovieTopRatingAdapter() }
    private val movieDramaAdapter by lazy { MovieDramaAdapter() }
    private val movieActionAdapter by lazy { MovieActionAdapter() }
    private val movieComedyAdapter by lazy { MovieComedyAdapter() }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            launch {
                movieViewModel.movieNewest.collectLatest {
                    movieNewestAdapter.submitList(it)
                }
            }
            launch {
                movieViewModel.movieTopRating.collectLatest {
                    movieTopRatingAdapter.submitList(it)
                }
            }
            launch {
                movieViewModel.movieDrama.collectLatest {
                    movieDramaAdapter.submitList(it)
                }
            }
            launch {
                movieViewModel.movieAction.collectLatest {
                    movieActionAdapter.submitList(it)
                }
            }
            launch {
                movieViewModel.movieComedy.collectLatest {
                    movieComedyAdapter.submitList(it)
                }
            }
        }

        bind {
            // set adapter
            newestAdapter = movieNewestAdapter
            topRatingAdapter = movieTopRatingAdapter
            dramaAdapter = movieDramaAdapter
            actionAdapter = movieActionAdapter
            comedyAdapter = movieComedyAdapter
        }

        binding.movieMainScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->

            if (scrollY > 1100) {
                binding.movieMainHeader.setBackgroundColor(resources.getColor(R.color.black))
            }
            if (oldScrollY > scrollY && scrollY < 1100) {
                binding.movieMainHeader.setBackgroundColor(resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
            }
        }

        movieNewestAdapter.setViewAndMovieListener(object : MovieNewestAdapter.OnClickListener {
            override fun sendViewAndMovie(img: View, movie: Movie) {
                movieViewModel.sendMovieDataForDetail(movie)
                (activity as MovieActivity).navDetailFragment()
            }
        })
        movieActionAdapter.setViewAndMovieListener(object : MovieActionAdapter.OnClickListener {
            override fun sendViewAndMovie(img: View, movie: Movie) {
                movieViewModel.sendMovieDataForDetail(movie)
                (activity as MovieActivity).navDetailFragment()
            }
        })

        movieDramaAdapter.setViewAndMovieListener(object : MovieDramaAdapter.OnClickListener {
            override fun sendViewAndMovie(img: View, movie: Movie) {
                movieViewModel.sendMovieDataForDetail(movie)
                (activity as MovieActivity).navDetailFragment()
            }
        })

        movieTopRatingAdapter.setViewAndMovieListener(object :
            MovieTopRatingAdapter.OnClickListener {
            override fun sendViewAndMovie(img: View, movie: Movie) {
                movieViewModel.sendMovieDataForDetail(movie)
                (activity as MovieActivity).navDetailFragment()
            }
        })

        movieComedyAdapter.setViewAndMovieListener(object : MovieComedyAdapter.OnClickListener {
            override fun sendViewAndMovie(img: View, movie: Movie) {
                movieViewModel.sendMovieDataForDetail(movie)
                (activity as MovieActivity).navDetailFragment()
            }
        })
        return view
    }
}