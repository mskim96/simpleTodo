package com.msk.simpletodo.presentation.viewModel.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.model.MovieWithCategory
import com.msk.simpletodo.domain.usecase.movie.GetLocalMovieUseCase
import com.msk.simpletodo.domain.usecase.movie.GetRemoteMovieUseCase
import com.msk.simpletodo.domain.usecase.movie.SaveMovieLocalUseCase
import com.msk.simpletodo.presentation.view.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getRemoteMovieUseCase: GetRemoteMovieUseCase,
    private val saveMovieLocalUseCase: SaveMovieLocalUseCase,
    private val getLocalMovieUseCase: GetLocalMovieUseCase
) :
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLocalMovieUseCase.execute().collect {
                if (it.size < 15) {
                    getRemoteMovie()
                    return@collect
                }
                getMoviesToNewest()
                getMoviesToRating()
                getMoviesToGenre("Drama")
                getMoviesToGenre("Action")
                getMoviesToGenre("Comedy")
            }
        }
    }

    /**
     * Get Movie data from local database
     */
    private val _movieNewest: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    val movieNewest = _movieNewest.asStateFlow()

    private val _movieTopRating: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    val movieTopRating = _movieTopRating.asStateFlow()

    private val _movieDrama: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    val movieDrama = _movieDrama.asStateFlow()

    private val _movieAction: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    val movieAction = _movieAction.asStateFlow()

    private val _movieComedy: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    val movieComedy = _movieComedy.asStateFlow()

    private val _movieDetailData: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val movieDetailData = _movieDetailData.asStateFlow()


    private fun getMoviesToNewest() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getLocalMovieUseCase.execute()
        }.onSuccess { data ->
            data.collect {
                _movieNewest.emit(it.subList(0, 15).toList())
            }
        }.onFailure { return@onFailure }
    }

    private fun getMoviesToRating() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getLocalMovieUseCase.getMoviesToRating()
        }.onSuccess { data ->
            data.collect {
                if (it.size < 15) getTopRatingMovies() else _movieTopRating.emit(
                    it.subList(
                        0,
                        15
                    ).toList()
                )
            }
        }.onFailure { return@onFailure }
    }

    private fun getMoviesToGenre(genres: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getLocalMovieUseCase.getMoviesToGenre(genres)
        }.onSuccess { data ->
            data.collect {
                when (genres) {
                    "Drama" -> if (it.size > 15) _movieDrama.emit(
                        it.subList(
                            0,
                            15
                        )
                    ) else _movieDrama.emit(it)
                    "Comedy" -> if (it.size > 15) _movieComedy.emit(
                        it.subList(
                            0,
                            15
                        )
                    ) else _movieComedy.emit(it)
                    "Action" -> if (it.size > 15) _movieAction.emit(
                        it.subList(
                            0,
                            15
                        )
                    ) else _movieAction.emit(it)
                }
            }
        }.onFailure { return@onFailure }
    }

    /**
     * Movie data for Remote data
     * TODO : Change function name
     */
    private fun getRemoteMovie() = viewModelScope.launch(Dispatchers.IO) {
        for (i in 1..10) {
            runCatching {
                getRemoteMovieUseCase.execute(i)
            }.onSuccess { data ->
                data.collectLatest {
                    saveMovieLocalUseCase.execute(it)
                }
            }.onFailure { return@onFailure }
        }
        getMoviesToNewest()
        getMoviesToRating()
        getMoviesToGenre("Drama")
        getMoviesToGenre("Action")
        getMoviesToGenre("Comedy")
    }

    private fun getTopRatingMovies() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getRemoteMovieUseCase.getTopRatingMovies()
        }.onSuccess { data ->
            data.collectLatest {
                saveMovieLocalUseCase.execute(it)
                _movieTopRating.emit(it)
            }
        }.onFailure { return@onFailure }
    }


    /**
     * Fragment Method
     */
    fun sendMovieDataForDetail(movie: Movie) = viewModelScope.launch {
        _movieDetailData.emit(movie)
    }
}
