package com.msk.simpletodo.presentation.hilt

import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.todo.TodoDatasource
import com.msk.simpletodo.data.datasource.todo.TodoDatasourceImpl
import com.msk.simpletodo.data.model.todo.TodoDao
import com.msk.simpletodo.data.repository.TodoRepositoryImpl
import com.msk.simpletodo.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Singleton
    @Provides
    fun todoDao(appDatabase: AppDatabase): TodoDao {
        return appDatabase.todoDao()
    }

    @Singleton
    @Provides
    fun provideTodoDatasource(todoDao: TodoDao): TodoDatasource {
        return TodoDatasourceImpl(todoDao)
    }

    @Singleton
    @Provides
    fun provideTodoRepository(todoDatasource: TodoDatasource): TodoRepository {
        return TodoRepositoryImpl(todoDatasource)
    }
}