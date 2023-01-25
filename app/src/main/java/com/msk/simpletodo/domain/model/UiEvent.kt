package com.msk.simpletodo.domain.model

sealed class UiEvent<out T> {
    class Success<T>(val data: T) : UiEvent<T>()
    class Failed<T>(val message: T) : UiEvent<T>()
}
