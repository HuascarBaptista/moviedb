package com.hebs.moviedb.data.source.remote

import android.content.Context
import com.hebs.moviedb.R
import com.hebs.moviedb.data.mappers.ResourceEntityToSectionEntityMapper
import com.hebs.moviedb.data.mappers.VideoEntityToVideoMediaMapper
import com.hebs.moviedb.data.model.SectionEntity
import com.hebs.moviedb.data.model.SectionEntityType
import com.hebs.moviedb.data.remote.service.TvShowService
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.tools.applySchedulers
import com.hebs.moviedb.tools.mapResultApplySchedules
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class TvShowRemoteDataSource @Inject constructor(
    private val tvShowService: TvShowService,
    private val mapper: ResourceEntityToSectionEntityMapper,
    private val videoMapper: VideoEntityToVideoMediaMapper,
    private val context: Context
) {

    fun getPopularTvShows(): Single<SectionEntity> =
        tvShowService.getPopularTvShows().map {
            mapper.fromEntity(
                entity = it,
                categoryName = context.getString(R.string.section_title_popular_tv_shows),
                categoryType = SectionEntityType.POPULAR_TV_SHOWS
            )
        }.applySchedulers()

    fun getTopRatedTvShows(): Single<SectionEntity> =
        tvShowService.getTopRatedTvShows().map {
            mapper.fromEntity(
                entity = it,
                categoryName = context.getString(R.string.section_title_top_rated_tv_shows),
                categoryType = SectionEntityType.TOP_RATED_TV_SHOWS
            )
        }.applySchedulers()

    fun getTvShowVideos(id: Int): Single<List<VideoMedia>> =
        tvShowService.getTvShowMedia(id).mapResultApplySchedules(videoMapper)

    fun search(term: String): Single<SectionEntity> =
        tvShowService.search(term).map {
            mapper.fromEntity(
                entity = it,
                categoryName = context.getString(R.string.section_title_search),
                categoryType = SectionEntityType.SEARCH
            )
        }.applySchedulers()
}