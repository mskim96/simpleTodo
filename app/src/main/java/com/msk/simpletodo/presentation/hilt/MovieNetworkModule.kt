package com.msk.simpletodo.presentation.hilt

import com.msk.simpletodo.data.api.MovieApiInterface
import com.msk.simpletodo.data.api.MovieApiClient
import com.msk.simpletodo.data.datasource.movie.*
import com.msk.simpletodo.data.repository.MovieRepositoryImpl
import com.msk.simpletodo.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieNetworkModule {

    @Singleton
    @Provides
    fun provideMovieApiInterface(retrofit: Retrofit): MovieApiInterface {
        return retrofit.create(MovieApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieRemoteDatasource(movieApiInterface: MovieApiInterface): MovieRemoteDatasource {
        return MovieRemoteDatasourceImpl(movieApiInterface)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieLocalDatasource: MovieLocalDatasource,
        movieRemoteDatasource: MovieRemoteDatasource
    ): MovieRepository {
        return MovieRepositoryImpl(movieLocalDatasource, movieRemoteDatasource)
    }
}