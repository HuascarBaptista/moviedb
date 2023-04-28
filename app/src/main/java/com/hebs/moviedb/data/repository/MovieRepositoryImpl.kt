package com.hebs.moviedb.data.repository

import android.content.Context
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
import com.hebs.moviedb.data.source.remote.MovieRemoteDataSource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.tools.applyIoSchedulers
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlin.reflect.KFunction0

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
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
                storeSectionData(section)
                section
            }.onErrorResumeNext {
                getLocalResourceSectionBySectionType(categoryType)
            }
    }

    private fun getLocalResourceSectionBySectionType(categoryType: SectionType) =
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

    private fun storeSectionData(section: ResourceSection) {
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

    override fun getVideoMedia(resourceId: Int): Single<List<VideoMedia>> {
        return movieRemoteDataSource.getVideoMedia(resourceId).applyIoSchedulers()
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

    override fun search(term: String) =
        getMovieSection(
            SectionType.SEARCH_MOVIES,
            context.getString(R.string.section_title_search_movies),
            movieRemoteDataSource::getTopRatedMovies
        )

}
