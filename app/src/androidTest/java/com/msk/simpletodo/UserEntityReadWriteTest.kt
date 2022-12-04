package com.msk.simpletodo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.model.UserDao
import com.msk.simpletodo.data.model.UserEntity
import com.msk.simpletodo.presentation.util.encryptECB
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserEntityReadWriteTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val userPassword = "12345".encryptECB()
        val user = UserEntity(email = "test@email.com", password = userPassword, username = "minseong")
        val testPassword = "12345".encryptECB()
        userDao.createAccount(user)
        val getUser = userDao.getAllUser().get(0)
        assertThat(getUser.password, equalTo(testPassword))
    }
}