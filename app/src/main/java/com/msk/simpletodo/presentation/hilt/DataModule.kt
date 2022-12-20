package com.msk.simpletodo.presentation.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.auth.AuthDatasource
import com.msk.simpletodo.data.datasource.auth.AuthDatasourceImpl
import com.msk.simpletodo.data.datasource.todo.TodoDatasource
import com.msk.simpletodo.data.datasource.todo.TodoDatasourceImpl
import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.model.movie.MovieDao
import com.msk.simpletodo.data.model.todo.TodoCategoryDao
import com.msk.simpletodo.data.model.todo.TodoDao
import com.msk.simpletodo.data.repository.AuthRepositoryImpl
import com.msk.simpletodo.data.repository.TodoRepositoryImpl
import com.msk.simpletodo.domain.repository.AuthRepository
import com.msk.simpletodo.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)


    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile("settings") })
    }

    @Singleton
    @Provides
    fun userDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

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
    fun movieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideAuthDatasource(userDao: UserDao): AuthDatasource {
        return AuthDatasourceImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideTodoDatasource(todoDao: TodoDao, todoCategoryDao: TodoCategoryDao): TodoDatasource {
        return TodoDatasourceImpl(todoDao, todoCategoryDao)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authDatasource: AuthDatasource): AuthRepository {
        return AuthRepositoryImpl(authDatasource)
    }

    @Singleton
    @Provides
    fun provideTodoRepository(todoDatasource: TodoDatasource): TodoRepository {
        return TodoRepositoryImpl(todoDatasource)
    }
}