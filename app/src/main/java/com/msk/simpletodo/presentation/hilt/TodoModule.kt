package com.msk.simpletodo.presentation.hilt

import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.task.TaskDatasource
import com.msk.simpletodo.data.datasource.task.TaskDatasourceImpl
import com.msk.simpletodo.data.model.task.TaskDao
import com.msk.simpletodo.data.repository.TaskRepositoryImpl
import com.msk.simpletodo.domain.repository.TaskRepository
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
    fun todoDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.todoDao()
    }

    @Singleton
    @Provides
    fun provideTodoDatasource(taskDao: TaskDao): TaskDatasource {
        return TaskDatasourceImpl(taskDao)
    }

    @Singleton
    @Provides
    fun provideTodoRepository(taskDatasource: TaskDatasource): TaskRepository {
        return TaskRepositoryImpl(taskDatasource)
    }
}