package com.hebs.moviedb.data.source.remote

import com.hebs.moviedb.data.mappers.ResourceEntityToTvShowMapper
import com.hebs.moviedb.data.mappers.VideoEntityToVideoMediaMapper
import com.hebs.moviedb.data.remote.service.TvShowService
import com.hebs.moviedb.domain.model.TVShow
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.tools.mapResultApplySchedules
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class TvShowRemoteDataSource @Inject constructor(
    private val tvShowService: TvShowService,
    private val mapper: ResourceEntityToTvShowMapper,
    private val videoMapper: VideoEntityToVideoMediaMapper,
) {

    fun getPopularTvShows(): Single<List<TVShow>> =
        tvShowService.getPopularTvShows().mapResultApplySchedules(mapper)

    fun getTopRatedTvShows(): Single<List<TVShow>> =
        tvShowService.getTopRatedTvShows().mapResultApplySchedules(mapper)

    fun getTvShowVideos(id: Int): Single<List<VideoMedia>> =
        tvShowService.getTvShowMedia(id).mapResultApplySchedules(videoMapper)

    fun search(term: String): Single<List<TVShow>> =
        tvShowService.search(term).mapResultApplySchedules(mapper)
}