package com.hebs.moviedb.data.source.remote

import com.hebs.moviedb.data.model.api.GenresListApiResponse
import com.hebs.moviedb.data.model.api.ResultVideoApiResponse
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.remote.service.TvShowService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class TvShowRemoteDataSource @Inject constructor(
    private val tvShowService: TvShowService,
) {

    fun getPopularTvShows(): Single<ResultsApiResponse> =
        tvShowService.getPopularTvShows()

    fun getTopRatedTvShows(): Single<ResultsApiResponse> =
        tvShowService.getTopRatedTvShows()

    fun getVideoMedia(id: Int): Single<ResultVideoApiResponse> =
        tvShowService.getTvShowMedia(id)

    fun search(query: String): Observable<ResultsApiResponse> =
        tvShowService.search(query)

    fun getGenreList(): Single<GenresListApiResponse> =
        tvShowService.getGenreList()

    fun getByGenre(genreId: Int): Single<ResultsApiResponse> =
        tvShowService.getTvShowByGenre(genreId)
}