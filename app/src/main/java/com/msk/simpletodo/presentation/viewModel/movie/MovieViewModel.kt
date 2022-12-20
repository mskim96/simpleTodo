package com.msk.simpletodo.presentation.viewModel.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.movie.Movie
import com.msk.simpletodo.domain.usecase.movie.GetLocalMovieUseCase
import com.msk.simpletodo.domain.usecase.movie.GetRemoteMovieUseCase
import com.msk.simpletodo.domain.usecase.movie.SaveMovieLocalUseCase
import com.msk.simpletodo.presentation.view.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getRemoteMovieUseCase: GetRemoteMovieUseCase,
    private val saveMovieLocalUseCase: SaveMovieLocalUseCase,
    private val getLocalMovieUseCase: GetLocalMovieUseCase
) :
    ViewModel() {

//    private val _movieResponse: MutableStateFlow<UiState<List<Movie>>> =
//        MutableStateFlow(UiState.Loading)
//    val movieResponse = _movieResponse.asStateFlow()

    /**
     * Get Movie data from local database
     */
    private val _movieLocalData: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieLocalData = _movieLocalData.asStateFlow()

    init {
        getLocalMovies()
    }

    /**
     * Movie data for Main Fragment
     * TODO: insert type of genre movie data (get more data and save local)
     */
    private fun getLocalMovies() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getLocalMovieUseCase.execute()
        }.onSuccess {
            it.collectLatest { data -> // get request local db but data is null or Empty
                if (data.isNullOrEmpty()) getRemoteMovie() else _movieLocalData // execute remote data
                    .emit(UiState.Success(data))
            }
        }.onFailure { return@onFailure }
    }


    /**
     * Movie data for Remote data
     * it is get newest 50 movie data
     * TODO : Change function name
     */
    private fun getRemoteMovie() = viewModelScope.launch(Dispatchers.IO) {
        for (i in 1..3) { // when i call this movie api, 20 movies of data can be imported at a time
            runCatching { // so create page parameter and get page 1,2,3
                getRemoteMovieUseCase.execute(page = i)
            }.onSuccess { data ->
                data.collectLatest { saveMovieLocalUseCase.execute(it) }
            }.onFailure { return@onFailure }
        }
    }
}