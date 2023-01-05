package com.msk.simpletodo.presentation.viewModel.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.model.MovieGenre
import com.msk.simpletodo.domain.usecase.movie.GetLocalMovieUseCase
import com.msk.simpletodo.domain.usecase.movie.GetRemoteMovieUseCase
import com.msk.simpletodo.domain.usecase.movie.SaveMovieLocalUseCase
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.view.base.UiState.Loading.getSuccessData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
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
        getMoviesByNewestRemote(1)
        getMoviesByRatingRemote(8, 1)
        getMoviesByGenreRemote(MovieGenre.Drama, 1)
        getMoviesByGenreRemote(MovieGenre.Action, 1)
        getMoviesByGenreRemote(MovieGenre.Comedy, 1)
        getMoviesByGenreRemote(MovieGenre.Adventure, 1)
        getMoviesByGenreRemote(MovieGenre.Thriller, 1)
        getMoviesByGenreRemote(MovieGenre.Animation, 1)
    }

    /**
     * Get Movie data from local database
     */
    private val _movieNewest: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieNewest = _movieNewest.asStateFlow()

    private val _movieTopRating: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieTopRating = _movieTopRating.asStateFlow()

    private val _movieDrama: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieDrama = _movieDrama.asStateFlow()

    private val _movieAction: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieAction = _movieAction.asStateFlow()

    private val _movieComedy: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieComedy = _movieComedy.asStateFlow()

    private val _movieThriller: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieThriller = _movieThriller.asStateFlow()

    private val _movieAnimation: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieAnimation = _movieAnimation.asStateFlow()

    private val _movieAdventure: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieAdventure = _movieAdventure.asStateFlow()


    private val _movieDetailData: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val movieDetailData = _movieDetailData.asStateFlow()

    /**
     * Fragment Method
     */
    fun sendMovieDataForDetail(movie: Movie) = viewModelScope.launch {
        _movieDetailData.emit(movie)
    }

    fun getMoviesByNewestRemote(page: Int) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getRemoteMovieUseCase.getMoviesByNewestRemote(page)
        }.onSuccess { data ->
            data.collectLatest { data ->
                _movieNewest.emit(UiState.Success(data))
            }
        }.onFailure { throwable -> UiState.Error(throwable) }
    }

    fun getNewestPage(currentPage: Int) = viewModelScope.launch {
        getRemoteMovieUseCase.getMoviesByNewestRemote(currentPage).collectLatest { data ->
            _movieNewest.update { UiState.Success(it.getSuccessData()!!.plus(data)) }
        }
    }

    fun getTopRatingPage(currentPage: Int) = viewModelScope.launch {
        getRemoteMovieUseCase.getMoviesByRatingRemote(8, currentPage).collectLatest { data ->
            _movieTopRating.update { UiState.Success(it.getSuccessData()!!.plus(data)) }
        }
    }

    fun getGenrePage(genre: MovieGenre, currentPage: Int) = viewModelScope.launch {
        getRemoteMovieUseCase.getMoviesByGenreRemote(genre, currentPage).collectLatest { data ->
            when (genre) {
                is MovieGenre.Action -> _movieAction.update {
                    UiState.Success(
                        it.getSuccessData()!!.plus(data)
                    )
                }
                is MovieGenre.Drama -> _movieDrama.update {
                    UiState.Success(
                        it.getSuccessData()!!.plus(data)
                    )
                }
                is MovieGenre.Comedy -> _movieComedy.update {
                    UiState.Success(
                        it.getSuccessData()!!.plus(data)
                    )
                }
                is MovieGenre.Animation -> _movieAnimation.update {
                    UiState.Success(
                        it.getSuccessData()!!.plus(data)
                    )
                }
                is MovieGenre.Thriller -> _movieThriller.update {
                    UiState.Success(
                        it.getSuccessData()!!.plus(data)
                    )
                }
                is MovieGenre.Adventure -> _movieAdventure.update {
                    UiState.Success(
                        it.getSuccessData()!!.plus(data)
                    )
                }
            }
        }
    }


    fun getMoviesByRatingRemote(rating: Int, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getRemoteMovieUseCase.getMoviesByRatingRemote(rating, page)
        }.onSuccess { data ->
            data.collectLatest {
                _movieTopRating.emit(UiState.Success(it))
            }
        }.onFailure { throwable -> UiState.Error(throwable) }
    }

    fun getMoviesByGenreRemote(genres: MovieGenre, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getRemoteMovieUseCase.getMoviesByGenreRemote(genres, page)
            }.onSuccess { data ->
                data.collectLatest {
                    when (genres) {
                        is MovieGenre.Action -> _movieAction.emit(UiState.Success(it))
                        is MovieGenre.Drama -> _movieDrama.emit(UiState.Success(it))
                        is MovieGenre.Comedy -> _movieComedy.emit(UiState.Success(it))
                        is MovieGenre.Animation -> _movieAnimation.emit(UiState.Success(it))
                        is MovieGenre.Thriller -> _movieThriller.emit(UiState.Success(it))
                        is MovieGenre.Adventure -> _movieAdventure.emit(UiState.Success(it))
                    }
                }
            }.onFailure { throwable -> UiState.Error(throwable) }
        }

    // search method
    fun getMoviesByQueryRemote(query: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        getRemoteMovieUseCase.getMoviesByQueryRemote(query, page).collectLatest {
//            UiState.Success(query, it))
        }
    }
}
