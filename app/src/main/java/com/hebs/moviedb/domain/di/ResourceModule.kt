package com.hebs.moviedb.domain.di

import android.content.Context
import com.hebs.moviedb.data.mappers.ApiResponseToGenreMapper
import com.hebs.moviedb.data.mappers.ApiResponseToResourceSectionMapper
import com.hebs.moviedb.data.mappers.ApiResponseToVideoMediaMapper
import com.hebs.moviedb.data.mappers.GenreEntityToGenreMapper
import com.hebs.moviedb.data.mappers.GenreToGenreEntityMapper
import com.hebs.moviedb.data.mappers.ResourceSectionToResourceEntityMapper
import com.hebs.moviedb.data.mappers.ResourceSectionToSectionEntityMapper
import com.hebs.moviedb.data.mappers.SectionEntityToResourceSectionMapper
import com.hebs.moviedb.data.mappers.VideoMediaEntityToVideoMediaMapper
import com.hebs.moviedb.data.mappers.VideoMediaToVideoMediaEntityMapper
import com.hebs.moviedb.data.remote.service.MovieService
import com.hebs.moviedb.data.remote.service.TvShowService
import com.hebs.moviedb.data.repository.MovieRepositoryImpl
import com.hebs.moviedb.data.repository.TvShowRepositoryImpl
import com.hebs.moviedb.data.source.local.AppDatabase
import com.hebs.moviedb.data.source.local.source.ResourceDataSource
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
    fun provideMovieLocalDataSource(appDatabase: AppDatabase): ResourceDataSource {
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
        localDataSource: ResourceDataSource,
        apiResponseToResourceSectionMapper: ApiResponseToResourceSectionMapper,
        resourceSectionToSectionEntityMapper: ResourceSectionToSectionEntityMapper,
        sectionToResourceEntityMapper: ResourceSectionToResourceEntityMapper,
        sectionEntityToResourceSectionMapper: SectionEntityToResourceSectionMapper,
        apiResponseToVideoMediaMapper: ApiResponseToVideoMediaMapper,
        videoMediaEntityToVideoMediaMapper: VideoMediaEntityToVideoMediaMapper,
        videoMediaToVideoMediaEntityMapper: VideoMediaToVideoMediaEntityMapper,
        apiResponseToGenreMapper: ApiResponseToGenreMapper,
        genreToGenreEntityMapper: GenreToGenreEntityMapper,
        genreEntityToGenreMapper: GenreEntityToGenreMapper,
        @ApplicationContext context: Context
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSource,
            localDataSource,
            apiResponseToResourceSectionMapper,
            resourceSectionToSectionEntityMapper,
            sectionToResourceEntityMapper,
            sectionEntityToResourceSectionMapper,
            apiResponseToVideoMediaMapper,
            videoMediaEntityToVideoMediaMapper,
            videoMediaToVideoMediaEntityMapper,
            apiResponseToGenreMapper,
            genreToGenreEntityMapper,
            genreEntityToGenreMapper,
            context
        )
    }

    @Provides
    fun provideTvShowRepository(
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        localDataSource: ResourceDataSource,
        apiResponseToResourceSectionMapper: ApiResponseToResourceSectionMapper,
        resourceSectionToSectionEntityMapper: ResourceSectionToSectionEntityMapper,
        sectionToResourceEntityMapper: ResourceSectionToResourceEntityMapper,
        sectionEntityToResourceSectionMapper: SectionEntityToResourceSectionMapper,
        apiResponseToVideoMediaMapper: ApiResponseToVideoMediaMapper,
        videoMediaEntityToVideoMediaMapper: VideoMediaEntityToVideoMediaMapper,
        videoMediaToVideoMediaEntityMapper: VideoMediaToVideoMediaEntityMapper,
        apiResponseToGenreMapper: ApiResponseToGenreMapper,
        genreToGenreEntityMapper: GenreToGenreEntityMapper,
        genreEntityToGenreMapper: GenreEntityToGenreMapper,
        @ApplicationContext context: Context
    ): TvShowRepository {
        return TvShowRepositoryImpl(
            tvShowRemoteDataSource,
            localDataSource,
            apiResponseToResourceSectionMapper,
            resourceSectionToSectionEntityMapper,
            sectionToResourceEntityMapper,
            sectionEntityToResourceSectionMapper,
            apiResponseToVideoMediaMapper,
            videoMediaEntityToVideoMediaMapper,
            videoMediaToVideoMediaEntityMapper,
            apiResponseToGenreMapper,
            genreToGenreEntityMapper,
            genreEntityToGenreMapper,
            context
        )
    }
}