package com.msk.simpletodo.domain.usecase.auth

import com.msk.simpletodo.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.signOut()
}