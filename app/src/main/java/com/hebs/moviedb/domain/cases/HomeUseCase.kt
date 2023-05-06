package com.hebs.moviedb.domain.cases

import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.domain.repository.TvShowRepository
import com.hebs.moviedb.tools.applySchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) {
    fun getData(): Flowable<List<ResourceSection>> {
        val popularMoviesSingle = moviesRepository.getPopularMoviesSection().applySchedulers()
        val topRatedMoviesSingle = moviesRepository.getTopRatedMoviesSection().applySchedulers()
        val popularTvShowsSingle = tvShowRepository.getPopularTvShowsSection().applySchedulers()
        val topRatedTvShowsSingle = tvShowRepository.getTopRatedTvShowsSection().applySchedulers()

        return Single.merge(
            popularMoviesSingle,
            popularTvShowsSingle,
            topRatedMoviesSingle,
            topRatedTvShowsSingle
        )
            .map { listOf(it) }
    }
}