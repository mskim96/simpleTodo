package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.AuthDatasource
import com.msk.simpletodo.data.model.UserEntity
import com.msk.simpletodo.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val datasource: AuthDatasource) :
    AuthRepository {
    override suspend fun createAccount(email: String, password: String, username: String): Long {
        val newUser = UserEntity(email = email, password = password, username = username)
        return datasource.createAccount(newUser)
    }

    override suspend fun getUserByEmail(email: String): UserEntity {
        return datasource.getUserByEmail(email)
    }
}