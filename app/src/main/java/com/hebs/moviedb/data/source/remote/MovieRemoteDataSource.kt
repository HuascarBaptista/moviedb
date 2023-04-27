package com.hebs.moviedb.data.source.remote

import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.model.api.VideoApiResponse
import com.hebs.moviedb.data.remote.service.MovieService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService,
) {

    fun getPopularMovies(): Single<ResultsApiResponse> =
        movieService.getPopularMovies()

    fun getTopRatedMovies(): Single<ResultsApiResponse> =
        movieService.getTopRatedMovies()

    fun getMovieVideos(id: Int): Single<List<VideoApiResponse>> =
        movieService.getMovieMedia(id)

    fun search(term: String): Single<ResultsApiResponse> =
        movieService.search(term)
}

