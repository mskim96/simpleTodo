package com.msk.simpletodo.presentation.viewModel.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.mapper.wrapper
import com.msk.simpletodo.domain.model.Movie
import com.msk.simpletodo.domain.model.MovieGenre
import com.msk.simpletodo.domain.model.MovieWrapper
import com.msk.simpletodo.domain.usecase.movie.GetMovieUseCase
import com.msk.simpletodo.domain.usecase.movie.SaveMovieUseCase
import com.msk.simpletodo.presentation.view.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val saveMovieUseCase: SaveMovieUseCase
) : ViewModel() {

    /**
     * Get Movie data from local database
     */
    private val _movieData: MutableStateFlow<UiState<List<MovieWrapper>>> =
        MutableStateFlow(UiState.Loading)
    val movieData = _movieData.asStateFlow()

    private val _movieCombineData: MutableStateFlow<List<MovieWrapper>> =
        MutableStateFlow(listOf())
    private val movieCombineData = _movieCombineData.asStateFlow()

    private val _movieSearchResult: MutableStateFlow<List<Movie>> =
        MutableStateFlow(listOf())
    val movieSearchResult = _movieSearchResult.asStateFlow()

    val movieSearchQuery = MutableStateFlow("")

    private val _movieDetailData: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val movieDetailData = _movieDetailData.asStateFlow()

    private val _movieRelData: MutableStateFlow<List<Movie>> =
        MutableStateFlow(listOf())
    val movieRelData = _movieRelData.asStateFlow()

    private val _movieLikeData: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    val movieLikeData = _movieLikeData.asStateFlow()

    init {
        getMoviesByAllData()
        combineMoviesCollect()
        getMoviesByQuery()
    }

    /**
     * Fragment Method
     */
    fun sendMovieDataForDetail(movie: Movie) = viewModelScope.launch {
        _movieDetailData.emit(movie)
    }

    private fun combineMoviesCollect() = viewModelScope.launch(Dispatchers.IO) {
        movieCombineData.collectLatest { _movieData.emit(UiState.Success(it)) }
    }

    private fun getMoviesByAllData() = viewModelScope.launch(Dispatchers.IO) {
        launch {
            combineFirstMoviesFlow()
            combineFirstGenreMoviesFlow()
            combineSecondGenreMoviesFlow()
        }
    }

    private fun getMoviesByNewest(): Flow<MovieWrapper> = flow {
        runCatching {
            getMovieUseCase.getMoviesByNewest()
        }.onSuccess { data ->
            data.collect {
                emit(wrapper("Newest", it))
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun getMoviesByRating(rating: Int): Flow<MovieWrapper> = flow {
        runCatching {
            getMovieUseCase.getMoviesByRating(rating)
        }.onSuccess { data ->
            data.collect {
                emit(wrapper("Top Rating Movie", it))
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun getMoviesByGenre(genres: MovieGenre): Flow<MovieWrapper> = flow {
        runCatching {
            getMovieUseCase.getMoviesByGenre(genres.value)
        }.onSuccess { data ->
            data.collect {
                emit(wrapper(genres.value, it))
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun getMoviesByQuery() = viewModelScope.launch(Dispatchers.IO) {
        movieSearchQuery.collectLatest {
            if (!it.isNullOrBlank()) {
                getMovieUseCase.getMoviesByQuery(it).collectLatest { data ->
                    _movieSearchResult.emit(data)
                }
            }
        }
    }

    fun getMoviesByGenreRel(genres: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getMovieUseCase.getMoviesByQuery(genres)
        }.onSuccess { data ->
            data.collectLatest {
                try {
                    val shuffle = (0..10).random()
                    _movieRelData.emit(it.slice(shuffle..shuffle + 3))
                } catch (e: Exception) {
                    _movieRelData.emit(it)
                }
            }
        }
    }

    fun getMoviesByLike(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getMovieUseCase.getMoviesByLike(uid)
        }.onSuccess { data ->
            data.collectLatest {
                _movieLikeData.emit(it)
            }
        }
    }

    fun saveMoviesByLike(uid: String, movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        saveMovieUseCase.saveMoviesByLike(uid, movie)
    }

    fun deleteMoviesByLike(uid: String, movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        saveMovieUseCase.deleteMoviesByLike(uid, movie)
    }

    private suspend fun combineFirstMoviesFlow() = combine(
        getMoviesByNewest(), getMoviesByRating(8),
    ) { m1: MovieWrapper, m2: MovieWrapper ->
        listOf(m1, m2)
    }.collectLatest { _movieCombineData.emit(it) }

    private suspend fun combineFirstGenreMoviesFlow() = combine(
        getMoviesByGenre(MovieGenre.ACTION),
        getMoviesByGenre(MovieGenre.ANIMATION),
        getMoviesByGenre(MovieGenre.ADVENTURE),
    ) { m1, m2, m3 ->
        listOf(m1, m2, m3)
    }.collectLatest { data -> _movieCombineData.update { it + data } }

    private suspend fun combineSecondGenreMoviesFlow() = combine(
        getMoviesByGenre(MovieGenre.COMEDY),
        getMoviesByGenre(MovieGenre.DRAMA),
        getMoviesByGenre(MovieGenre.THRILLER),
    ) { m1, m2, m3 ->
        listOf(m1, m2, m3)
    }.collectLatest { data -> _movieCombineData.update { it + data } }
}
