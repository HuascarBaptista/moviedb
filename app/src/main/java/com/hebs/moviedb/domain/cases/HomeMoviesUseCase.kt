package com.hebs.moviedb.domain.cases

import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.domain.repository.TvShowRepository
import com.hebs.moviedb.tools.applySchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeMoviesUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) {
    fun getData(): Flowable<List<ResourceSection>> {
        val popularMoviesSingle = moviesRepository.getPopularMoviesSection()
        val topRatedMoviesSingle = moviesRepository.getTopRatedMoviesSection()
        val popularTvShowsSingle = tvShowRepository.getPopularTvShowsSection()
        val topRatedTvShowsSingle = tvShowRepository.getTopRatedTvShowsSection()

        return Single.merge(
            popularMoviesSingle,
            popularTvShowsSingle,
            topRatedMoviesSingle,
            topRatedTvShowsSingle
        )
            .filter { it.isValid() }
            .map { listOf(it) }
            .applySchedulers()
    }
}