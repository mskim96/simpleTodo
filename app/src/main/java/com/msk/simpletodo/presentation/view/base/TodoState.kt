package com.msk.simpletodo.presentation.view.base

sealed class TodoState<out T> {
    object Loading : TodoState<Nothing>()
    data class Success<T>(val data: T) : TodoState<T>()
    data class Error(val error: Throwable?) : TodoState<Nothing>()
}
