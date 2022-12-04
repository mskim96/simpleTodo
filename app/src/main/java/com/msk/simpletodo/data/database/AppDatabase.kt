package com.msk.simpletodo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msk.simpletodo.data.model.UserDao
import com.msk.simpletodo.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}