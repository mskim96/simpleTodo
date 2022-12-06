package com.msk.simpletodo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.AuthDatasource
import com.msk.simpletodo.data.datasource.AuthDatasourceImpl
import com.msk.simpletodo.data.model.UserDao
import com.msk.simpletodo.data.repository.AuthRepositoryImpl
import com.msk.simpletodo.domain.auth.repository.AuthRepository
import com.msk.simpletodo.domain.auth.usecase.SignUpUseCase
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

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        userDao = db.userDao()
        datasource = AuthDatasourceImpl(userDao)
        repository = AuthRepositoryImpl(datasource)
        signUpUseCase = SignUpUseCase(repository)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
//        viewModel.createAccount(email = "123@test.com", "12345", "test")
//        CoroutineScope(Dispatchers.IO).launch {
//            assertThat(viewModel.userResult.value, equalTo(1L))
//        }
    }
}