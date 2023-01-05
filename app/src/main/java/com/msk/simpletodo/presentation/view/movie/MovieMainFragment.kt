package com.msk.simpletodo.presentation.view.movie

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.data.model.movie.movieDummy
import com.msk.simpletodo.databinding.FragmentMovieMainBinding
import com.msk.simpletodo.domain.model.MovieGenre
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.movie.MovieViewModel
import com.msk.simpletodo.presentation.viewModel.movie.adapter.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MovieMainFragment : BaseFragment<FragmentMovieMainBinding>(R.layout.fragment_movie_main) {

    private val movieViewModel by lazy { ViewModelProvider(requireActivity())[MovieViewModel::class.java] }

    private val movieNewestAdapter by lazy { MovieNewestAdapter { movie -> adapterOnClick(movie) } }
    private val movieTopRatingAdapter by lazy {
        MovieTopRatingAdapter { movie -> adapterOnClick(movie) }
    }
    private val movieDramaAdapter by lazy { MovieDramaAdapter { movie -> adapterOnClick(movie) } }
    private val movieActionAdapter by lazy { MovieActionAdapter { movie -> adapterOnClick(movie) } }
    private val movieComedyAdapter by lazy { MovieComedyAdapter { movie -> adapterOnClick(movie) } }
    private val movieAdventureAdapter by lazy {
        MovieAdventureAdapter { movie ->
            adapterOnClick(movie)
        }
    }
    private val movieAnimationAdapter by lazy {
        MovieAnimationAdapter { movie ->
            adapterOnClick(movie)
        }
    }
    private val movieThrillerAdapter by lazy { MovieThrillerAdapter { movie -> adapterOnClick(movie) } }

    private var newRecyclerViewState: Parcelable? = null
    private var topRecyclerViewState: Parcelable? = null
    private var dramaRecyclerViewState: Parcelable? = null
    private var actionRecyclerViewState: Parcelable? = null
    private var comedyRecyclerViewState: Parcelable? = null
    private var adventureRecyclerViewState: Parcelable? = null
    private var thrillerRecyclerViewState: Parcelable? = null
    private var animationRecyclerViewState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        (activity as MovieActivity).setBottomNavigation()

        lifecycleScope.launchWhenStarted {
            /**
             * Movie Newest launched
             */
            launch {
                movieViewModel.movieNewest.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieNewestAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieNewestAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
                }
            }


            /**
             * Movie TopRating launch
             */
            launch {
                movieViewModel.movieTopRating.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieTopRatingAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieTopRatingAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
                }
            }

            /**
             * Movie Drama launch
             */
            launch {
                movieViewModel.movieDrama.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieDramaAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieDramaAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
                }
            }

            /**
             * Movie Action launch
             */
            launch {
                movieViewModel.movieAction.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieActionAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieActionAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
                }
            }

            /**
             * Movie comedy launch
             */
            launch {
                movieViewModel.movieComedy.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieComedyAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieComedyAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
                }
            }

            /**
             * Movie Adventure launch
             */
            launch {
                movieViewModel.movieAdventure.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieAdventureAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieAdventureAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
                }
            }

            /**
             * Movie Animation launch
             */
            launch {
                movieViewModel.movieAnimation.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieAnimationAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieAnimationAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
                }
            }


            /**
             * Movie Thriller launch
             */
            launch {
                movieViewModel.movieThriller.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            movieThrillerAdapter.submitList(movieDummy)
                        }
                        is UiState.Success -> {
                            movieThrillerAdapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException()
                        }
                    }
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
            animationAdapter = movieAnimationAdapter
            thrillerAdapter = movieThrillerAdapter
            adventureAdapter = movieAdventureAdapter
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

        binding.newestRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let { movieViewModel.getNewestPage(it.div(20).plus(1)) }
                }
            }
        })

        binding.topRatingRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let { movieViewModel.getTopRatingPage(it.div(20).plus(1)) }
                }
            }
        })

        binding.dramaRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let { movieViewModel.getGenrePage(MovieGenre.Drama, it.div(20).plus(1)) }
                }
            }
        })

        binding.animationRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let {
                        movieViewModel.getGenrePage(
                            MovieGenre.Animation,
                            it.div(20).plus(1)
                        )
                    }
                }
            }
        })

        binding.adventureRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let {
                        movieViewModel.getGenrePage(
                            MovieGenre.Adventure,
                            it.div(20).plus(1)
                        )
                    }
                }
            }
        })

        binding.comedyRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let { movieViewModel.getGenrePage(MovieGenre.Comedy, it.div(20).plus(1)) }
                }
            }
        })

        binding.actionRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let { movieViewModel.getGenrePage(MovieGenre.Action, it.div(20).plus(1)) }
                }
            }
        })

        binding.thrillerRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = recyclerView.layoutManager?.itemCount
                if (!recyclerView.canScrollHorizontally(1)) {
                    page?.let {
                        movieViewModel.getGenrePage(
                            MovieGenre.Thriller,
                            it.div(20).plus(1)
                        )
                    }
                }
            }
        })


        return view
    }

    override fun onResume() {
        super.onResume()
        setSavedRecyclerViewState()
    }

    override fun onPause() {
        super.onPause()
        saveRecyclerViewState()
    }

    private fun saveRecyclerViewState() {
        newRecyclerViewState = binding.newestRecycler.layoutManager?.onSaveInstanceState()
        topRecyclerViewState = binding.topRatingRecycler.layoutManager?.onSaveInstanceState()
        dramaRecyclerViewState = binding.dramaRecycler.layoutManager?.onSaveInstanceState()
        actionRecyclerViewState = binding.actionRecycler.layoutManager?.onSaveInstanceState()
        thrillerRecyclerViewState = binding.thrillerRecycler.layoutManager?.onSaveInstanceState()
        comedyRecyclerViewState = binding.comedyRecycler.layoutManager?.onSaveInstanceState()
        adventureRecyclerViewState = binding.adventureRecycler.layoutManager?.onSaveInstanceState()
        animationRecyclerViewState = binding.animationRecycler.layoutManager?.onSaveInstanceState()
    }

    private fun setSavedRecyclerViewState() {
        binding.newestRecycler.layoutManager?.onRestoreInstanceState(newRecyclerViewState)
        binding.topRatingRecycler.layoutManager?.onRestoreInstanceState(topRecyclerViewState)
        binding.dramaRecycler.layoutManager?.onRestoreInstanceState(dramaRecyclerViewState)
        binding.actionRecycler.layoutManager?.onRestoreInstanceState(actionRecyclerViewState)
        binding.thrillerRecycler.layoutManager?.onRestoreInstanceState(thrillerRecyclerViewState)
        binding.comedyRecycler.layoutManager?.onRestoreInstanceState(comedyRecyclerViewState)
        binding.adventureRecycler.layoutManager?.onRestoreInstanceState(adventureRecyclerViewState)
        binding.animationRecycler.layoutManager?.onRestoreInstanceState(animationRecyclerViewState)
    }

    private fun adapterOnClick(movie: Movie) {
        movieViewModel.sendMovieDataForDetail(movie)
        (activity as MovieActivity).navDetailFragment()
    }
}

