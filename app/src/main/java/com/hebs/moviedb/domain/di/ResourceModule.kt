package com.hebs.moviedb.domain.di

import android.content.Context
import com.hebs.moviedb.data.mappers.ApiResponseToSectionMapper
import com.hebs.moviedb.data.mappers.CategoryTypeMapper
import com.hebs.moviedb.data.mappers.SectionResourcesEntityToSectionMapper
import com.hebs.moviedb.data.mappers.SectionToResourcesEntityMapper
import com.hebs.moviedb.data.mappers.SectionToSectionEntityMapper
import com.hebs.moviedb.data.remote.service.MovieService
import com.hebs.moviedb.data.remote.service.TvShowService
import com.hebs.moviedb.data.repository.MovieRepositoryImpl
import com.hebs.moviedb.data.repository.TvShowRepositoryImpl
import com.hebs.moviedb.data.source.local.AppDatabase
import com.hebs.moviedb.data.source.local.source.LocalDataSource
import com.hebs.moviedb.data.source.remote.MovieRemoteDataSource
import com.hebs.moviedb.data.source.remote.TvShowRemoteDataSource
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ResourceModule {

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(appDatabase: AppDatabase): LocalDataSource {
        return appDatabase.localDataSource()
    }

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
        localDataSource: LocalDataSource,
        apiResponseToSectionMapper: ApiResponseToSectionMapper,
        sectionToSectionEntityMapper: SectionToSectionEntityMapper,
        sectionToResourceEntityMapper: SectionToResourcesEntityMapper,
        sectionResourcesEntityToSectionMapper: SectionResourcesEntityToSectionMapper,
        categoryTypeMapper: CategoryTypeMapper,
        @ApplicationContext context: Context
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSource,
            localDataSource,
            apiResponseToSectionMapper,
            sectionToSectionEntityMapper,
            sectionToResourceEntityMapper,
            sectionResourcesEntityToSectionMapper,
            categoryTypeMapper,
            context
        )
    }

    @Provides
    fun provideTvShowRepository(
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        localDataSource: LocalDataSource,
        apiResponseToSectionMapper: ApiResponseToSectionMapper,
        sectionToSectionEntityMapper: SectionToSectionEntityMapper,
        sectionToResourceEntityMapper: SectionToResourcesEntityMapper,
        sectionResourcesEntityToSectionMapper: SectionResourcesEntityToSectionMapper,
        categoryTypeMapper: CategoryTypeMapper,
        @ApplicationContext context: Context
    ): TvShowRepository {
        return TvShowRepositoryImpl(
            tvShowRemoteDataSource,
            localDataSource,
            apiResponseToSectionMapper,
            sectionToSectionEntityMapper,
            sectionToResourceEntityMapper,
            sectionResourcesEntityToSectionMapper,
            categoryTypeMapper,
            context
        )
    }
}