package com.msk.simpletodo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.auth.AuthDatasource
import com.msk.simpletodo.data.datasource.auth.AuthDatasourceImpl
import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.repository.AuthRepositoryImpl
import com.msk.simpletodo.domain.repository.AuthRepository
import com.msk.simpletodo.domain.usecase.auth.SignUpUseCase
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserEntityReadWriteTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase
    private lateinit var datasource: AuthDatasource
    private lateinit var repository: AuthRepository
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var signInUseCase: SignUpUseCase
    private lateinit var viewModel: AuthViewModel
}