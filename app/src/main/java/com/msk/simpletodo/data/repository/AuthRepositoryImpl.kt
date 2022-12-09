package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.auth.AuthDatasource
import com.msk.simpletodo.data.model.auth.UserEntity
import com.msk.simpletodo.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val datasource: AuthDatasource) :
    AuthRepository {
    override suspend fun createAccount(email: String, username: String) {
        val newUser = UserEntity(email = email, username = username)
        return datasource.createAccount(newUser)
    }
}