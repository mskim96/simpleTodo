package com.msk.simpletodo.presentation.hilt

import com.msk.simpletodo.data.api.MovieClient
import com.msk.simpletodo.data.api.MovieInterface
import com.msk.simpletodo.data.datasource.movie.*
import com.msk.simpletodo.data.model.movie.MovieDao
import com.msk.simpletodo.data.repository.MovieRepositoryImpl
import com.msk.simpletodo.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): MovieInterface {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideMovieRemoteDatasource(movieInterface: MovieInterface): MovieRemoteDatasource {
        return MovieRemoteDatasourceImpl(movieInterface)
    }

    @Singleton
    @Provides
    fun provideMovieLocalDatasource(movieDao: MovieDao): MovieLocalDatasource {
        return MovieLocalDatasourceImpl(movieDao)
    }


    @Singleton
    @Provides
    fun provideMovieRepository(
        movieLocalDatasource: MovieLocalDatasource,
        movieRemoteDatasource: MovieRemoteDatasource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDatasource, movieLocalDatasource)
    }
}