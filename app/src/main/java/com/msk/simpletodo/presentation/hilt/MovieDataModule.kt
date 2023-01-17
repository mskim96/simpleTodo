package com.msk.simpletodo.presentation.hilt

import com.google.firebase.firestore.FirebaseFirestore
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.datasource.movie.MovieLocalDatasource
import com.msk.simpletodo.data.datasource.movie.MovieLocalDatasourceImpl
import com.msk.simpletodo.data.model.movie.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDataModule {

    @Singleton
    @Provides
    fun movieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideMovieLocalDatasource(
        movieDao: MovieDao,
    ): MovieLocalDatasource {
        return MovieLocalDatasourceImpl(movieDao)
    }
}