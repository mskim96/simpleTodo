package com.msk.simpletodo.presentation.view.base

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T : Throwable?>(val message: T) : UiState<T>()

    fun <T> UiState<T>.getSuccessData(): T? = if (this is UiState.Success) {
        data
    } else {
        null
    }
}
