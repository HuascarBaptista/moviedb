package com.hebs.moviedb.domain.di

import android.app.Application
import com.hebs.moviedb.data.mappers.SectionEntityToResourceSectionMapper
import com.hebs.moviedb.data.remote.service.MovieService
import com.hebs.moviedb.data.remote.service.TvShowService
import com.hebs.moviedb.data.repository.MovieRepositoryImpl
import com.hebs.moviedb.data.repository.TvShowRepositoryImpl
import com.hebs.moviedb.data.source.remote.MovieRemoteDataSource
import com.hebs.moviedb.data.source.remote.TvShowRemoteDataSource
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.domain.repository.TvShowRepository
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
    fun provideMoviesRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        sectionEntityToResourceSectionMapper: SectionEntityToResourceSectionMapper
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource, sectionEntityToResourceSectionMapper)
    }

    @Provides
    fun provideTvShowRepository(
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        sectionEntityToResourceSectionMapper: SectionEntityToResourceSectionMapper
    ): TvShowRepository {
        return TvShowRepositoryImpl(tvShowRemoteDataSource, sectionEntityToResourceSectionMapper)
    }

    @Provides
    fun providesContext(app: Application) = app.applicationContext
}