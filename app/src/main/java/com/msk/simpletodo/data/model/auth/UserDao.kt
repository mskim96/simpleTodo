package com.msk.simpletodo.data.model.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user_information_table")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM user_information_table WHERE user_email =:email")
    fun getUserByEmail(email: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAccount(user: UserEntity): Long
}