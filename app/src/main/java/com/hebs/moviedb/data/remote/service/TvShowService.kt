package com.hebs.moviedb.data.remote.service

import com.hebs.moviedb.data.model.api.GenresListApiResponse
import com.hebs.moviedb.data.model.api.ResultVideoApiResponse
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
interface TvShowService {

    @GET("/3/tv/popular")
    fun getPopularTvShows(): Single<ResultsApiResponse>

    @GET("/3/tv/top_rated")
    fun getTopRatedTvShows(): Single<ResultsApiResponse>

    @GET("/3/tv/{tvShow_id}/videos")
    fun getTvShowMedia(@Path("tvShow_id") id: Int): Single<ResultVideoApiResponse>

    @GET("/3/search/tv")
    fun search(@Query("query") term: String): Observable<ResultsApiResponse>

    @GET("/3/genre/tv/list")
    fun getGenreList(): Single<GenresListApiResponse>

    @GET("/3/discover/tv")
    fun getTvShowByGenre(@Query("with_genres") id: Int): Single<ResultsApiResponse>

}