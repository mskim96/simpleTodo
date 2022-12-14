package com.msk.simpletodo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.msk.simpletodo.data.converter.TypeConverter
import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.model.auth.UserEntity
import com.msk.simpletodo.data.model.movie.MovieDao
import com.msk.simpletodo.data.model.movie.MovieEntity
import com.msk.simpletodo.data.model.todo.TodoCategory
import com.msk.simpletodo.data.model.todo.TodoCategoryDao
import com.msk.simpletodo.data.model.todo.TodoDao
import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors


@Database(
    entities = [UserEntity::class, TodoEntity::class, TodoCategory::class, MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun todoDao(): TodoDao
    abstract fun todoCategoryDao(): TodoCategoryDao
    abstract fun movieDao(): MovieDao

    companion object {
        fun getInstance(context: Context): AppDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, "todo-database")
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    Executors.newSingleThreadExecutor().execute {
                        // set Initial category item
                        runBlocking {
                            getInstance(context).todoCategoryDao()
                                .createCategory(TodoCategory.PERSONAL)
                            getInstance(context).todoCategoryDao()
                                .createCategory(TodoCategory.WORK)
                            getInstance(context).todoCategoryDao()
                                .createCategory(TodoCategory.STUDY)
                            getInstance(context).todoCategoryDao()
                                .createCategory(TodoCategory.OTHER)
                        }
                    }
                }
            }).build()
    }
}