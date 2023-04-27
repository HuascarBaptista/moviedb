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
import com.hebs.moviedb.data.source.remote.TvShowRemoteDataSource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.repository.TvShowRepository
import com.hebs.moviedb.tools.applyIoSchedulers
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlin.reflect.KFunction0

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: LocalDataSource,
    private val apiResponseToSectionMapper: ApiResponseToSectionMapper,
    private val sectionToSectionEntityMapper: SectionToSectionEntityMapper,
    private val sectionToResourceEntityMapper: SectionToResourcesEntityMapper,
    private val sectionResourcesEntityToSectionMapper: SectionResourcesEntityToSectionMapper,
    private val categoryTypeMapper: CategoryTypeMapper,
    @ApplicationContext private val context: Context
) : TvShowRepository {

    override fun getPopularTvShowsSection() = getTvShowSection(
        SectionType.POPULAR_TV_SHOWS,
        context.getString(R.string.section_title_popular_tv_shows),
        tvShowRemoteDataSource::getPopularTvShows
    )

    override fun getTopRatedTvShowsSection() = getTvShowSection(
        SectionType.TOP_RATED_TV_SHOWS,
        context.getString(R.string.section_title_top_rated_tv_shows),
        tvShowRemoteDataSource::getTopRatedTvShows
    )

    override fun getTvShowVideos(id: Int) {
        tvShowRemoteDataSource.getTvShowVideos(id)
    }

    override fun search(term: String) = getTvShowSection(
        SectionType.SEARCH_TV_SHOWS,
        context.getString(R.string.section_title_search_tv_shows),
        tvShowRemoteDataSource::getTopRatedTvShows
    )

    private fun getTvShowSection(
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
        tvShowLocalDataSource.getSectionBySectionType(categoryTypeMapper.mapToEntity(categoryType))
            .applyIoSchedulers()
            .map { sectionWithResources ->
                mapToResourceSection(sectionWithResources)
            }

    private fun mapToResourceSection(sectionWithResources: SectionWithResources): ResourceSection {
        val resources = getResourcesOrderBySectionType(
            sectionWithResources.section.categoryType, sectionWithResources
        )
        val sectionEntity = sectionWithResources.section
        return sectionResourcesEntityToSectionMapper.map(sectionEntity, resources)
    }

    private fun storeData(section: ResourceSection) {
        val sectionEntity = sectionToSectionEntityMapper.map(section)
        val resourcesEntity = sectionToResourceEntityMapper.map(section)
        tvShowLocalDataSource.insertAll(resourcesEntity, sectionEntity)
    }

    private fun getResourcesOrderBySectionType(
        sectionType: SectionEntityType, sectionWithResources: SectionWithResources
    ) = when {
        sectionType.isPopularType() -> sectionWithResources.resources.sortedByDescending { it.rating }
        sectionType.isTopRatedType() -> sectionWithResources.resources.sortedByDescending { it.score }
        sectionType.isSearchType() -> sectionWithResources.resources.sortedByDescending { it.id }
        else -> {
            sectionWithResources.resources
        }
    }
}
