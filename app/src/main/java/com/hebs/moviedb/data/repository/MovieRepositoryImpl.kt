package com.hebs.moviedb.data.repository

import android.content.Context
import com.hebs.moviedb.R
import com.hebs.moviedb.data.mappers.ApiResponseToSectionMapper
import com.hebs.moviedb.data.mappers.CategoryTypeMapper
import com.hebs.moviedb.data.mappers.SectionResourcesEntityToSectionMapper
import com.hebs.moviedb.data.mappers.SectionToResourcesEntityMapper
import com.hebs.moviedb.data.mappers.SectionToSectionEntityMapper
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.data.model.local.SectionWithResources
import com.hebs.moviedb.data.source.local.source.LocalDataSource
import com.hebs.moviedb.data.source.remote.MovieRemoteDataSource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.tools.applyIoSchedulers
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlin.reflect.KFunction0

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val apiResponseToSectionMapper: ApiResponseToSectionMapper,
    private val sectionToSectionEntityMapper: SectionToSectionEntityMapper,
    private val sectionToResourceEntityMapper: SectionToResourcesEntityMapper,
    private val sectionResourcesEntityToSectionMapper: SectionResourcesEntityToSectionMapper,
    private val categoryTypeMapper: CategoryTypeMapper,
    @ApplicationContext private val context: Context
) : MovieRepository {

    override fun getPopularMoviesSection() =
        getMovieSection(
            SectionType.POPULAR_MOVIES,
            context.getString(R.string.section_title_popular_movies),
            movieRemoteDataSource::getPopularMovies
        )

    override fun getTopRatedMoviesSection() =
        getMovieSection(
            SectionType.TOP_RATED_MOVIES,
            context.getString(R.string.section_title_top_rated_movies),
            movieRemoteDataSource::getTopRatedMovies
        )

    override fun getMovieVideos(id: Int) {
        movieRemoteDataSource.getMovieVideos(id)
    }

    override fun search(term: String) =
        getMovieSection(
            SectionType.SEARCH_MOVIES,
            context.getString(R.string.section_title_search_movies),
            movieRemoteDataSource::getTopRatedMovies
        )

    private fun getMovieSection(
        categoryType: SectionType,
        categoryName: String,
        remoteDataSourceCall: KFunction0<Single<ResultsApiResponse>>
    ): Single<ResourceSection> {

        return remoteDataSourceCall().map {
            apiResponseToSectionMapper.map(
                resources = it,
                categoryName = categoryName,
                categoryType = categoryType,
            )
        }.applyIoSchedulers()
            .map { section ->
                storeData(section)
                section
            }.onErrorResumeNext {
                retrieveLocalData(categoryType)
            }
    }

    private fun retrieveLocalData(categoryType: SectionType) =
        localDataSource
            .getSectionBySectionType(categoryTypeMapper.mapToEntity(categoryType))
            .applyIoSchedulers()
            .map { sectionWithResources ->
                mapToResourceSection(sectionWithResources)
            }

    private fun mapToResourceSection(sectionWithResources: SectionWithResources): ResourceSection {
        val resources = getResourcesOrderBySectionType(
            sectionWithResources.section.categoryType,
            sectionWithResources
        )
        val sectionEntity = sectionWithResources.section
        return sectionResourcesEntityToSectionMapper.map(sectionEntity, resources)
    }

    private fun storeData(section: ResourceSection) {
        val sectionEntity = sectionToSectionEntityMapper.map(section)
        val resourcesEntity = sectionToResourceEntityMapper.map(section)
        localDataSource.insertAll(resourcesEntity, sectionEntity)
    }

    private fun getResourcesOrderBySectionType(
        sectionType: SectionEntityType,
        sectionWithResources: SectionWithResources
    ) = when {
        sectionType.isPopularType() -> sectionWithResources.resources.sortedByDescending { it.rating }
        sectionType.isTopRatedType() -> sectionWithResources.resources.sortedByDescending { it.score }
        sectionType.isSearchType() -> sectionWithResources.resources.sortedByDescending { it.id }
        else -> {
            sectionWithResources.resources
        }
    }
}
