package com.msk.simpletodo.presentation.view.base

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Fail(val error: Throwable?) : UiState<Nothing>()
}

