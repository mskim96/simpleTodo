package com.msk.simpletodo.presentation.hilt

import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.todo.TodoDatasource
import com.msk.simpletodo.data.datasource.todo.TodoDatasourceImpl
import com.msk.simpletodo.data.model.todo.TodoCategoryDao
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
    fun todoCategoryDao(appDatabase: AppDatabase): TodoCategoryDao {
        return appDatabase.todoCategoryDao()
    }

    @Singleton
    @Provides
    fun provideTodoDatasource(todoDao: TodoDao, todoCategoryDao: TodoCategoryDao): TodoDatasource {
        return TodoDatasourceImpl(todoDao, todoCategoryDao)
    }

    @Singleton
    @Provides
    fun provideTodoRepository(todoDatasource: TodoDatasource): TodoRepository {
        return TodoRepositoryImpl(todoDatasource)
    }
}