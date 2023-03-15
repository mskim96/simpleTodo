package com.msk.simpletodo.presentation.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.auth.AuthDatasource
import com.msk.simpletodo.data.datasource.auth.AuthDatasourceImpl
import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.repository.AuthRepositoryImpl
import com.msk.simpletodo.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun firebaseAuth(): FirebaseAuth =
        Firebase.auth

    @Singleton
    @Provides
    fun userDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile("settings") })
    }

    @Singleton
    @Provides
    fun provideAuthDatasource(userDao: UserDao): AuthDatasource {
        return AuthDatasourceImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authRemoteDatasource: FirebaseAuth,
        authLocalDatasource: AuthDatasource
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDatasource, authLocalDatasource)
    }
}