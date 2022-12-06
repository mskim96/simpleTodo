package com.msk.simpletodo.domain.auth.model

sealed class UserState<T>(
    open val data: T? = null,
    open val message: String? = null
) {
    data class Success<T>(override val data: T) : UserState<T>(data)
    data class Error<T>(override val message: String, override val data: T?) :
        UserState<T>(message = message, data = data)
}