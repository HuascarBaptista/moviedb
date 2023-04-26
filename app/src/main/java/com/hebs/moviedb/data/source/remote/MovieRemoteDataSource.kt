package com.hebs.moviedb.data.source.remote

import com.hebs.moviedb.data.mappers.ResourceEntityToMovieMapper
import com.hebs.moviedb.data.mappers.VideoEntityToVideoMediaMapper
import com.hebs.moviedb.data.remote.service.MovieService
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.tools.mapResourceResultApplySchedules
import com.hebs.moviedb.tools.mapResultApplySchedules
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService,
    private val mapper: ResourceEntityToMovieMapper,
    private val videoMapper: VideoEntityToVideoMediaMapper,
) {

    fun getPopularMovies(): Single<List<Movie>> =
        movieService.getPopularMovies().mapResourceResultApplySchedules(mapper)

    fun getTopRatedMovies(): Single<List<Movie>> =
        movieService.getTopRatedMovies().mapResourceResultApplySchedules(mapper)

    fun getMovieVideos(id: Int): Single<List<VideoMedia>> =
        movieService.getMovieMedia(id).mapResultApplySchedules(videoMapper)

    fun search(term: String): Single<List<Movie>> =
        movieService.search(term).mapResourceResultApplySchedules(mapper)
}

