package com.hebs.moviedb.data.remote.service

import com.hebs.moviedb.data.model.ResourceEntityResponse
import com.hebs.moviedb.data.model.VideoEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
interface TvShowService {

    @GET("/3/tv/popular")
    fun getPopularTvShows(): Single<ResourceEntityResponse>

    @GET("/3/tv/top_rated")
    fun getTopRatedTvShows(): Single<ResourceEntityResponse>

    @GET("/3/tv/{tvShow_id}/videos")
    fun getTvShowMedia(@Path("tvShow_id") id: Int): Single<List<VideoEntity>>

    @GET("/3/search/tv")
    fun search(@Query("query") term: String): Single<ResourceEntityResponse>

}