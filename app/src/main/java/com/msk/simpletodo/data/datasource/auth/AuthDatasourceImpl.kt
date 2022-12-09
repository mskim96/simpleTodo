package com.msk.simpletodo.data.datasource.auth

import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.model.auth.UserEntity
import javax.inject.Inject

class AuthDatasourceImpl @Inject constructor(private val userDao: UserDao) : AuthDatasource {
    override suspend fun createAccount(user: UserEntity){
        userDao.createAccount(user = user)
    }
}