package com.msk.simpletodo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msk.simpletodo.data.converter.DateConverter
import com.msk.simpletodo.data.converter.TypeConverter
import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.model.auth.UserEntity
import com.msk.simpletodo.data.model.movie.MovieDao
import com.msk.simpletodo.data.model.movie.MovieEntity
import com.msk.simpletodo.data.model.task.TaskDao
import com.msk.simpletodo.data.model.task.TaskEntity

@Database(
    entities = [UserEntity::class, TaskEntity::class, MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [TypeConverter::class, DateConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun todoDao(): TaskDao
    abstract fun movieDao(): MovieDao

    companion object {
        fun getInstance(context: Context): AppDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, "todo-database").build()
    }
}