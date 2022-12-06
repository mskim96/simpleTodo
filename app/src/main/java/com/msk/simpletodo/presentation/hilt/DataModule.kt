package com.msk.simpletodo.presentation.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.AuthDatasource
import com.msk.simpletodo.data.datasource.AuthDatasourceImpl
import com.msk.simpletodo.data.model.UserDao
import com.msk.simpletodo.data.repository.AuthRepositoryImpl
import com.msk.simpletodo.domain.auth.repository.AuthRepository
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
    fun userDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            "todo-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideAuthDatasource(userDao: UserDao): AuthDatasource {
        return AuthDatasourceImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authDatasource: AuthDatasource): AuthRepository {
        return AuthRepositoryImpl(authDatasource)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile("settings") })
    }

}