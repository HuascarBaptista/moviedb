package com.hebs.moviedb.domain.di

import com.hebs.moviedb.data.remote.service.MovieService
import com.hebs.moviedb.data.remote.service.TvShowService
import com.hebs.moviedb.data.repository.MovieRepositoryImpl
import com.hebs.moviedb.data.source.remote.MovieRemoteDataSource
import com.hebs.moviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ResourceModule {

    @Provides
    fun provideTvShowService(retrofit: Retrofit): TvShowService {
        return retrofit.create(TvShowService::class.java)
    }

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    fun provideMoviesRepository(movieRemoteDataSource: MovieRemoteDataSource): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource)
    }
}