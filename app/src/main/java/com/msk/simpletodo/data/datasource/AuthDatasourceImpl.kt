package com.msk.simpletodo.data.datasource

import com.msk.simpletodo.data.model.UserDao
import com.msk.simpletodo.data.model.UserEntity
import javax.inject.Inject

class AuthDatasourceImpl @Inject constructor(private val userDao: UserDao) : AuthDatasource {
    override suspend fun createAccount(user: UserEntity): Long {
        return userDao.createAccount(user = user)
    }

    override suspend fun getUserByEmail(email: String): UserEntity {
        return userDao.getUserByEmail(email = email)
    }
}