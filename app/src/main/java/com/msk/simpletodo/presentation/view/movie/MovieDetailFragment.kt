package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentMovieDetailBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import com.msk.simpletodo.presentation.viewModel.movie.adapter.MovieGenreAdapter
import kotlinx.coroutines.flow.collectLatest


class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        (activity as MovieActivity).hideBottomNavigation()
        (activity as MovieActivity).navDetailInnerFragment(0)
        lifecycleScope.launchWhenStarted {
            movieViewModel.movieDetailData.collectLatest {
                Glide.with(binding.root).load(it?.coverImg).into(binding.movieThumbnail)
                binding.movie = it
                binding.genreRecyclerView.adapter = MovieGenreAdapter(it!!)
            }
        }

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
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
}