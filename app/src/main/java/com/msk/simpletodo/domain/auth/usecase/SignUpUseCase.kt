package com.msk.simpletodo.domain.auth.usecase

import com.msk.simpletodo.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String, username: String): Long {
        return repository.createAccount(email, password, username)
    }
}