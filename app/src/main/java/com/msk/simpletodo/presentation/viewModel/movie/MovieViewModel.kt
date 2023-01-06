package com.msk.simpletodo.presentation.viewModel.movie

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


    private val _movieSearchResult: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieSearchResult = _movieSearchResult.asStateFlow()

    val movieSearchQuery = MutableStateFlow("")

    private val _movieDetailData: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val movieDetailData = _movieDetailData.asStateFlow()

    private val _movieRelData: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val movieRelData = _movieRelData.asStateFlow()

    init {
        getMoviesByNewestLocal()
        getMoviesByRatingLocal(8)
        getMoviesByGenreLocal(MovieGenre.Drama)
        getMoviesByGenreLocal(MovieGenre.Action)
        getMoviesByGenreLocal(MovieGenre.Comedy)
        getMoviesByGenreLocal(MovieGenre.Adventure)
        getMoviesByGenreLocal(MovieGenre.Thriller)
        getMoviesByGenreLocal(MovieGenre.Animation)
        getMoviesByQuery()
    }

    /**
     * Fragment Method
     */
    fun sendMovieDataForDetail(movie: Movie) = viewModelScope.launch {
        _movieDetailData.emit(movie)
    }

    /**
     * Local Method
     */

    private fun getMoviesByNewestLocal() = viewModelScope.launch(Dispatchers.IO) {
        getLocalMovieUseCase.getMoviesFromLocal().collectLatest {
            if (it.isNullOrEmpty()) {
                getMoviesByNewestRemote(1)
            } else {
                _movieNewest.emit(UiState.Success(it))
            }
        }
    }

    private fun getMoviesByGenreLocal(genres: MovieGenre) = viewModelScope.launch(Dispatchers.IO) {
        getLocalMovieUseCase.getMoviesByGenreLocal(genres).collectLatest {
            if (it.isNullOrEmpty()) {
                getMoviesByGenreRemote(genres, 1)
            } else {
                when (genres) {
                    is MovieGenre.Action -> _movieAction.emit(UiState.Success(it))
                    is MovieGenre.Drama -> _movieDrama.emit(UiState.Success(it))
                    is MovieGenre.Comedy -> _movieComedy.emit(UiState.Success(it))
                    is MovieGenre.Animation -> _movieAnimation.emit(UiState.Success(it))
                    is MovieGenre.Thriller -> _movieThriller.emit(UiState.Success(it))
                    is MovieGenre.Adventure -> _movieAdventure.emit(UiState.Success(it))
                }
            }
        }
    }

    fun getMoviesByGenreRelated(genres: String) = viewModelScope.launch(Dispatchers.IO) {
        getLocalMovieUseCase.getMoviesByGenreRelated(genres).collectLatest {
            _movieRelData.emit(UiState.Success(it.slice(0..13)))
        }
    }

    private fun getMoviesByRatingLocal(rating: Int) = viewModelScope.launch(Dispatchers.IO) {
        getLocalMovieUseCase.getMoviesByRatingLocal().collectLatest {
            if (it.isNullOrEmpty()) {
                getMoviesByRatingRemote(rating, 1)
            } else {
                _movieTopRating.emit(UiState.Success(it))
            }
        }
    }

    fun getMoviesByQuery() = viewModelScope.launch(Dispatchers.IO) {
        movieSearchQuery.collectLatest {
            if (!it.isNullOrBlank()) {
                getLocalMovieUseCase.getMoviesByQuery(it).collectLatest { data ->
                    _movieSearchResult.emit(UiState.Success(data))
                }
            }
        }
    }

    /**
     * Remote Method
     */

    private fun getMoviesByNewestRemote(page: Int) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getRemoteMovieUseCase.getMoviesByNewestRemote(page)
        }.onSuccess { data ->
            data.collectLatest {
                saveMovieLocalUseCase.execute(it)
                _movieNewest.emit(UiState.Success(it))
            }
        }.onFailure { throwable -> UiState.Error(throwable) }
    }

    private fun getMoviesByRatingRemote(rating: Int, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getRemoteMovieUseCase.getMoviesByRatingRemote(rating, page)
            }.onSuccess { data ->
                data.collectLatest {
                    saveMovieLocalUseCase.execute(it)
                    _movieTopRating.emit(UiState.Success(it))
                }
            }.onFailure { throwable -> UiState.Error(throwable) }
        }

    private fun getMoviesByGenreRemote(genres: MovieGenre, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getRemoteMovieUseCase.getMoviesByGenreRemote(genres, page)
            }.onSuccess { data ->
                data.collectLatest {
                    when (genres) {
                        is MovieGenre.Action -> {
                            saveMovieLocalUseCase.execute(it)
                            _movieAction.emit(UiState.Success(it))
                        }
                        is MovieGenre.Drama -> {
                            saveMovieLocalUseCase.execute(it)
                            _movieDrama.emit(UiState.Success(it))
                        }
                        is MovieGenre.Comedy -> {
                            saveMovieLocalUseCase.execute(it)
                            _movieComedy.emit(UiState.Success(it))
                        }
                        is MovieGenre.Animation -> {
                            saveMovieLocalUseCase.execute(it)
                            _movieAnimation.emit(UiState.Success(it))
                        }
                        is MovieGenre.Thriller -> {
                            saveMovieLocalUseCase.execute(it)
                            _movieThriller.emit(UiState.Success(it))
                        }
                        is MovieGenre.Adventure -> {
                            saveMovieLocalUseCase.execute(it)
                            _movieAdventure.emit(UiState.Success(it))
                        }
                    }
                }
            }.onFailure { throwable -> UiState.Error(throwable) }
        }

    // search method
//    fun getMoviesByQueryRemote(query: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
//        getRemoteMovieUseCase.getMoviesByQueryRemote(query, page).collectLatest {
//            UiState.Success(query, it))
//        }
//    }

    /**
     * get Movie data more (infinite scroll)
     */

    fun getNewestPage(currentPage: Int) = viewModelScope.launch {
        getRemoteMovieUseCase.getMoviesByNewestRemote(currentPage).collectLatest { data ->
            saveMovieLocalUseCase.execute(data)
            _movieNewest.update { UiState.Success(it.getSuccessData()!!.plus(data)) }
        }
    }

    fun getTopRatingPage(currentPage: Int) = viewModelScope.launch {
        getRemoteMovieUseCase.getMoviesByRatingRemote(8, currentPage).collectLatest { data ->
            saveMovieLocalUseCase.execute(data)
            _movieTopRating.update { UiState.Success(it.getSuccessData()!!.plus(data)) }
        }
    }

    fun getGenrePage(genre: MovieGenre, currentPage: Int) = viewModelScope.launch {
        getRemoteMovieUseCase.getMoviesByGenreRemote(genre, currentPage).collectLatest { data ->
            when (genre) {
                is MovieGenre.Action -> {
                    _movieAction.update {
                        saveMovieLocalUseCase.execute(data)
                        UiState.Success(
                            it.getSuccessData()!!.plus(data)
                        )
                    }
                }
                is MovieGenre.Drama -> {
                    _movieDrama.update {
                        saveMovieLocalUseCase.execute(data)
                        UiState.Success(
                            it.getSuccessData()!!.plus(data)
                        )
                    }
                }
                is MovieGenre.Comedy -> {
                    saveMovieLocalUseCase.execute(data)
                    _movieComedy.update {
                        UiState.Success(
                            it.getSuccessData()!!.plus(data)
                        )
                    }
                }
                is MovieGenre.Animation -> {
                    saveMovieLocalUseCase.execute(data)
                    _movieAnimation.update {
                        UiState.Success(
                            it.getSuccessData()!!.plus(data)
                        )
                    }
                }
                is MovieGenre.Thriller -> {
                    saveMovieLocalUseCase.execute(data)
                    _movieThriller.update {
                        UiState.Success(
                            it.getSuccessData()!!.plus(data)
                        )
                    }
                }
                is MovieGenre.Adventure -> {
                    saveMovieLocalUseCase.execute(data)
                    _movieAdventure.update {
                        UiState.Success(
                            it.getSuccessData()!!.plus(data)
                        )
                    }
                }
            }
        }
    }
}
