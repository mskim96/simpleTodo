package com.msk.simpletodo.presentation.view.base

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: Throwable?) : Result<Nothing>()

    fun <T> Result<T>.successOrNull(): T? = if (this is Result.Success<T>) {
        data
    } else {
        null
    }
}
