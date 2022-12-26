package com.msk.simpletodo.domain.usecase.auth

import com.msk.simpletodo.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String, username: String) {
        repository.createAccount(email, password, username)
    }
}