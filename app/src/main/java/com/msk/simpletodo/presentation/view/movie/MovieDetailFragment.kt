package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentMovieDetailBinding
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import com.msk.simpletodo.presentation.viewModel.movie.adapter.MovieGenreAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }
    private val auth by lazy { Firebase.auth }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        (activity as MovieActivity).hideBottomNavigation()
        (activity as MovieActivity).navDetailInnerFragment(0)

        lifecycleScope.launchWhenStarted {
            movieViewModel.getMoviesByLike(uid = auth.uid.toString())
            movieViewModel.movieDetailData.collectLatest { movie ->
                Glide.with(binding.root).load(movie?.coverImg).into(binding.movieThumbnail)
                binding.genreRecyclerView.adapter = MovieGenreAdapter(movie!!)
                binding.movie = movie
                movieViewModel.movieLikeData.map {
                    it.filter { it.id == movie.id }
                }.collectLatest {
                    toggleButtonImage(it)
                }
            }
        }

        binding.movieLikeButton.setOnClickListener {

        }

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> (activity as MovieActivity).navDetailInnerFragment(0)
                    1 -> (activity as MovieActivity).navDetailInnerFragment(1)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        (view?.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
        return view
    }

    private fun toggleButtonImage(movie: List<Movie>) {
        if (movie.isNotEmpty()) {
            with(binding.movieLikeButton) {
                val fillImage = resources.getDrawable(R.drawable.movie_like_fill_icon)
                setCompoundDrawablesWithIntrinsicBounds(null, fillImage, null, null)
            }
            binding.movieLikeButton.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    movieViewModel.movieDetailData.collectLatest {
                        movieViewModel.deleteMoviesByLike(auth.uid.toString(), it!!)
                    }
                }
                with(binding.movieLikeButton) {
                    val image = resources.getDrawable(R.drawable.movie_like_icon)
                    setCompoundDrawablesWithIntrinsicBounds(null, image, null, null)
                    isEnabled = false
                }
                Snackbar.make(it, "Delete Complete!", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            with(binding.movieLikeButton) {
                val emptyImage =
                    resources.getDrawable(R.drawable.movie_like_icon)
                setCompoundDrawablesWithIntrinsicBounds(
                    null, emptyImage, null, null
                )
            }
            binding.movieLikeButton.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    movieViewModel.movieDetailData.collectLatest {
                        movieViewModel.saveMoviesByLike(auth.uid.toString(), it!!)
                    }
                }
                with(binding.movieLikeButton) {
                    val fillImage = resources.getDrawable(R.drawable.movie_like_fill_icon)
                    setCompoundDrawablesWithIntrinsicBounds(null, fillImage, null, null)
                    isEnabled = false
                }
                Snackbar.make(it, "Add Complete!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
