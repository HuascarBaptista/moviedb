package com.hebs.moviedb.data.remote.service

import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.model.api.VideoApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
interface MovieService {

    @GET("/3/movie/popular")
    fun getPopularMovies(): Single<ResultsApiResponse>

    @GET("/3/movie/top_rated")
    fun getTopRatedMovies(): Single<ResultsApiResponse>

    @GET("/3/movie/{movie_id}/videos")
    fun getMovieMedia(@Path("movie_id") id: Int): Single<List<VideoApiResponse>>

    @GET("/3/search/movie")
    fun search(@Query("query") term: String): Single<ResultsApiResponse>

}