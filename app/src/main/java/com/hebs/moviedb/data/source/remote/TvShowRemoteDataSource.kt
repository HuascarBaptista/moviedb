package com.hebs.moviedb.data.source.remote

import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.model.api.VideoApiResponse
import com.hebs.moviedb.data.remote.service.TvShowService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class TvShowRemoteDataSource @Inject constructor(
    private val tvShowService: TvShowService,
) {

    fun getPopularTvShows(): Single<ResultsApiResponse> =
        tvShowService.getPopularTvShows()

    fun getTopRatedTvShows(): Single<ResultsApiResponse> =
        tvShowService.getTopRatedTvShows()

    fun getTvShowVideos(id: Int): Single<List<VideoApiResponse>> =
        tvShowService.getTvShowMedia(id)

    fun search(term: String): Single<ResultsApiResponse> =
        tvShowService.search(term)
}