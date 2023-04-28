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
interface MovieService {

    @GET("/3/movie/popular")
    fun getPopularMovies(): Single<ResultsApiResponse>

    @GET("/3/movie/top_rated")
    fun getTopRatedMovies(): Single<ResultsApiResponse>

    @GET("/3/movie/{movie_id}/videos")
    fun getMovieMedia(@Path("movie_id") id: Int): Single<ResultVideoApiResponse>

    @GET("/3/search/movie")
    fun search(@Query("query") term: String): Observable<ResultsApiResponse>

    @GET("/3/genre/movie/list")
    fun getGenreList(): Single<GenresListApiResponse>

    @GET("/3/discover/movie")
    fun getTvShowByGenre(@Query("with_genres") id: Int): Single<ResultsApiResponse>

}