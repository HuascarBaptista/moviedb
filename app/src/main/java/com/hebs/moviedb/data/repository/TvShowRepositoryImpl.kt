package com.hebs.moviedb.data.repository

import android.content.Context
import android.util.Log
import com.hebs.moviedb.R
import com.hebs.moviedb.data.mappers.ApiResponseToSectionMapper
import com.hebs.moviedb.data.mappers.ApiResponseToVideoMediaMapper
import com.hebs.moviedb.data.mappers.CategoryTypeMapper
import com.hebs.moviedb.data.mappers.SectionResourcesEntityToSectionMapper
import com.hebs.moviedb.data.mappers.SectionToResourcesEntityMapper
import com.hebs.moviedb.data.mappers.SectionToSectionEntityMapper
import com.hebs.moviedb.data.mappers.VideoMediaEntityToVideoMediaMapper
import com.hebs.moviedb.data.mappers.VideoMediaToVideoMediaEntityMapper
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.data.model.local.SectionWithResources
import com.hebs.moviedb.data.source.local.source.ResourceDataSource
import com.hebs.moviedb.data.source.remote.TvShowRemoteDataSource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.domain.repository.TvShowRepository
import com.hebs.moviedb.tools.applyIoSchedulers
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: ResourceDataSource,
    private val apiResponseToSectionMapper: ApiResponseToSectionMapper,
    private val sectionToSectionEntityMapper: SectionToSectionEntityMapper,
    private val sectionToResourceEntityMapper: SectionToResourcesEntityMapper,
    private val sectionResourcesEntityToSectionMapper: SectionResourcesEntityToSectionMapper,
    private val categoryTypeMapper: CategoryTypeMapper,
    private val apiResponseToVideoMediaMapper: ApiResponseToVideoMediaMapper,
    private val videoMediaEntityToVideoMediaMapper: VideoMediaEntityToVideoMediaMapper,
    private val videoMediaToVideoMediaEntityMapper: VideoMediaToVideoMediaEntityMapper,
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

    private fun getTvShowSection(
        categoryType: SectionType,
        categoryName: String,
        remoteDataSourceCall: () -> Single<ResultsApiResponse>
    ): Single<ResourceSection> {

        return remoteDataSourceCall().applyIoSchedulers().map {
            apiResponseToSectionMapper.map(
                resources = it,
                categoryName = categoryName,
                categoryType = categoryType,
            )
        }.map { section ->
            storeSectionData(section)
            section
        }.onErrorResumeNext {
            retrieveLocalData(categoryType)
        }
    }

    private fun retrieveLocalData(categoryType: SectionType) =
        localDataSource.getSectionBySectionType(categoryTypeMapper.mapToEntity(categoryType))
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

    private fun storeSectionData(section: ResourceSection) {
        val sectionEntity = sectionToSectionEntityMapper.map(section)
        val resourcesEntity = sectionToResourceEntityMapper.map(section)
        localDataSource.insertAll(resourcesEntity, sectionEntity)
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

    override fun getVideoMedia(resourceId: Int): Single<List<VideoMedia>> {
        return tvShowRemoteDataSource.getVideoMedia(resourceId).applyIoSchedulers()
            .map { videoList ->
                videoList.results.map {
                    apiResponseToVideoMediaMapper.map(it)
                }
            }.map { videoMediaList ->
                storeVideoMediaList(videoMediaList, resourceId)
                videoMediaList
            }.onErrorResumeNext {
                getLocalVideoMediaByResourceId(resourceId)
            }
    }

    private fun storeVideoMediaList(videoMediaList: List<VideoMedia>, resourceId: Int) {
        val videoMediaEntity =
            videoMediaList.map { videoMediaToVideoMediaEntityMapper.map(it, resourceId) }
        localDataSource.insertVideoMediaEntity(videoMediaEntity)
    }

    private fun getLocalVideoMediaByResourceId(resourceId: Int): Single<List<VideoMedia>> =
        localDataSource
            .getVideosMediaByResourceId(resourceId)
            .applyIoSchedulers()
            .map { videoMediaList ->
                videoMediaList.map {
                    videoMediaEntityToVideoMediaMapper.map(it)
                }
            }

    override fun search(query: String): Observable<ResourceSection> {
        val categoryType = SectionType.SEARCH
        val categoryName = context.getString(R.string.section_title_search, query)
        return tvShowRemoteDataSource.search(query).map {
            apiResponseToSectionMapper.map(
                resources = it,
                categoryName = categoryName,
                categoryType = categoryType,
            )
        }.applyIoSchedulers()
            .map { section ->
                storeSectionData(section)
                section
            }.onErrorResumeNext {
                Log.e("hebshebs", " onErrorResumeNext $categoryName")
                retrieveLocalData(categoryType).toObservable()
            }
    }

}
