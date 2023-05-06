package com.hebs.moviedb.data.repository

import android.content.Context
import com.hebs.moviedb.R
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
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.data.model.local.SectionWithResources
import com.hebs.moviedb.data.source.local.source.ResourceDataSource
import com.hebs.moviedb.data.source.remote.TvShowRemoteDataSource
import com.hebs.moviedb.domain.model.Genre
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
    private val apiResponseToResourceSectionMapper: ApiResponseToResourceSectionMapper,
    private val resourceSectionToSectionEntityMapper: ResourceSectionToSectionEntityMapper,
    private val sectionToResourceEntityMapper: ResourceSectionToResourceEntityMapper,
    private val sectionEntityToResourceSectionMapper: SectionEntityToResourceSectionMapper,
    private val apiResponseToVideoMediaMapper: ApiResponseToVideoMediaMapper,
    private val videoMediaEntityToVideoMediaMapper: VideoMediaEntityToVideoMediaMapper,
    private val videoMediaToVideoMediaEntityMapper: VideoMediaToVideoMediaEntityMapper,
    private val apiResponseToGenreMapper: ApiResponseToGenreMapper,
    private val genreToGenreEntityMapper: GenreToGenreEntityMapper,
    private val genreEntityToGenreMapper: GenreEntityToGenreMapper,
    @ApplicationContext private val context: Context
) : TvShowRepository {

    override fun getPopularTvShowsSection() =
        getTvShowSection(
            SectionType.POPULAR_TV_SHOWS,
            context.getString(R.string.section_title_popular_tv_shows),
            tvShowRemoteDataSource::getPopularTvShows
        )

    override fun getTopRatedTvShowsSection() =
        getTvShowSection(
            SectionType.TOP_RATED_TV_SHOWS,
            context.getString(R.string.section_title_top_rated_tv_shows),
            tvShowRemoteDataSource::getTopRatedTvShows
        )

    private fun getTvShowSection(
        categoryType: SectionType,
        categoryName: String,
        remoteDataSourceCall: () -> Single<ResultsApiResponse>
    ): Single<ResourceSection> {

        return remoteDataSourceCall().map {
            apiResponseToResourceSectionMapper.map(
                resources = it,
                categoryName = categoryName,
                categoryType = categoryType,
            )
        }.applyIoSchedulers()
            .map { section ->
                storeSectionData(section)
                section
            }.onErrorResumeNext {
                getLocalResourceSectionByCategoryName(categoryName)
            }
    }

    private fun getLocalResourceSectionByCategoryName(categoryName: String) =
        localDataSource
            .getSectionWithResourcesByCategoryName(categoryName)
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
        return sectionEntityToResourceSectionMapper.map(sectionEntity, resources)
    }

    private fun storeSectionData(section: ResourceSection) {
        val sectionEntity = resourceSectionToSectionEntityMapper.map(section)
        val resourcesEntity = sectionToResourceEntityMapper.map(section)
        localDataSource.insertAll(resourcesEntity, sectionEntity)
    }

    private fun getResourcesOrderBySectionType(
        sectionType: SectionEntityType,
        sectionWithResources: SectionWithResources
    ) = when {
        sectionType.isGenreType() || sectionType.isSearchType() || sectionType.isPopularType() -> sectionWithResources.resources.sortedByDescending { it.rating }
        sectionType.isTopRatedType() -> sectionWithResources.resources.sortedByDescending { it.score }
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
        val categoryType = SectionType.SEARCH_TV_SHOW
        val categoryName = context.getString(R.string.section_title_search, query)
        return tvShowRemoteDataSource.search(query).map {
            apiResponseToResourceSectionMapper.map(
                resources = it,
                categoryName = categoryName,
                categoryType = categoryType,
            )
        }.applyIoSchedulers()
            .map { section ->
                storeSectionData(section)
                section
            }.onErrorResumeNext {
                getLocalResourceSectionByCategoryName(categoryName).toObservable()
            }
    }

    override fun getGenreList(): Single<List<Genre>> {
        return tvShowRemoteDataSource.getGenreList().applyIoSchedulers()
            .map { genreList ->
                genreList.genres.map {
                    apiResponseToGenreMapper.map(it)
                }
            }.map { genreList ->
                storeGenreList(genreList)
                genreList
            }.onErrorResumeNext {
                getLocalGenreList()
            }
    }

    override fun storeGenreList(genreList: List<Genre>) {
        val genreEntityList = genreList.map { genreToGenreEntityMapper.map(it) }
        localDataSource.insertGenreList(genreEntityList)
    }

    private fun getLocalGenreList(): Single<List<Genre>> =
        localDataSource
            .getGenreList()
            .applyIoSchedulers()
            .map { genreList ->
                genreList.map {
                    genreEntityToGenreMapper.map(it)
                }
            }

    override fun getByGenre(genre: Genre): Single<ResourceSection> {
        val categoryType = SectionType.BY_GENRE_TV_SHOWS
        val categoryName = context.getString(R.string.section_title_by_genre_tv_shows, genre.name)

        return tvShowRemoteDataSource.getByGenre(genre.id).applyIoSchedulers()
            .map { byGenreList ->
                apiResponseToResourceSectionMapper.map(
                    resources = byGenreList,
                    categoryName = categoryName,
                    categoryType = categoryType,
                )
            }.map { section ->
                storeSectionData(section)
                section
            }.onErrorResumeNext {
                getLocalResourceSectionByCategoryName(categoryName)
            }
    }
}
