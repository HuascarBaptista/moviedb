package com.hebs.moviedb.domain.cases

import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DiscoverMoviesUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
) {

    fun getData(): Single<List<Movie>> = moviesRepository.fetchPopularMovies()
}