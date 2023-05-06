package com.hebs.moviedb.data.source.remote

import com.hebs.moviedb.data.model.api.GenresListApiResponse
import com.hebs.moviedb.data.model.api.ResultVideoApiResponse
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.data.remote.service.MovieService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService,
) {

    fun getPopularMovies(): Single<ResultsApiResponse> =
        movieService.getPopularMovies()

    fun getTopRatedMovies(): Single<ResultsApiResponse> =
        movieService.getTopRatedMovies()

    fun getVideoMedia(id: Int): Single<ResultVideoApiResponse> =
        movieService.getMovieMedia(id)

    fun search(query: String): Observable<ResultsApiResponse> =
        movieService.search(query)

    fun getGenreList(): Single<GenresListApiResponse> =
        movieService.getGenreList()

    fun getByGenre(genreId: Int): Single<ResultsApiResponse> =
        movieService.getTvShowByGenre(genreId)
}

