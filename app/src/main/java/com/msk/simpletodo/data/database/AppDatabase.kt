package com.msk.simpletodo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msk.simpletodo.data.model.TodoDao
import com.msk.simpletodo.data.model.TodoEntity
import com.msk.simpletodo.data.model.UserDao
import com.msk.simpletodo.data.model.UserEntity

@Database(entities = [UserEntity::class, TodoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun todoDao(): TodoDao
}